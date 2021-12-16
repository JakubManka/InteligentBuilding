package pwr.inteligentbuilding.model;

import static pwr.inteligentbuilding.utils.RequestName.*;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.ReadResponse;
import org.opcfoundation.ua.core.ReadValueId;
import org.opcfoundation.ua.core.TimestampsToReturn;
import org.opcfoundation.ua.core.WriteResponse;

import java.util.ArrayList;
import java.util.List;

import pwr.inteligentbuilding.utils.ActionName;
import pwr.inteligentbuilding.utils.opcUtils.ConnectionThread.ThreadWrite;
import pwr.inteligentbuilding.utils.opcUtils.ManagerOPC;
import pwr.inteligentbuilding.utils.opcUtils.SessionElement;
import pwr.inteligentbuilding.R;

public class Light implements Device {
    private final String nodeId;
    private final int namespace;
    private final ManagerOPC manager;
    private Variant status;
    private final List<Action> actions;
    private final String room;

    public Light(String nodeId, int namespace, String room) {
        this.nodeId = nodeId;
        this.namespace = namespace;
        this.manager = ManagerOPC.getIstance();
        this.room = room;
        status = new Variant("undefined");
        actions = new ArrayList<>();
        setActions();
    }

    @Override
    public void turnOn() {
        Variant value_write = new Variant(true);
        write(value_write, nodeId + REQ_ON);
    }

    @Override
    public void turnOff() {
        Variant value_write = new Variant(true);
        write(value_write, nodeId + REQ_OFF);
    }

    @Override
    public void setActions() {
        actions.add(new Action(nodeId + ActionName.getActions().get(0)));
        actions.add(new Action(nodeId + ActionName.getActions().get(1)));
        actions.add(new Action(nodeId + ActionName.getActions().get(2)));
        actions.add(new Action(nodeId + ActionName.getActions().get(3)));
        actions.add(new Action(nodeId + ActionName.getActions().get(4)));
        actions.add(new Action(nodeId + ActionName.getActions().get(5)));
        actions.add(new Action(nodeId + ActionName.getActions().get(6)));
        actions.add(new Action(nodeId + ActionName.getActions().get(7)));
        actions.add(new Action(nodeId + ActionName.getActions().get(8)));
        actions.add(new Action(nodeId + ActionName.getActions().get(9)));
    }

    @Override
    public void updateStatus() {
        SessionElement sessionElement = manager.getSessions().get(0);
        try {
            ReadResponse res = sessionElement.getSession().Read(null, 0d, TimestampsToReturn.Both,
                    new ReadValueId(new NodeId(namespace, nodeId + IS_ON), Attributes.Value, null, null));
            status = res.getResults()[0].getValue();
        } catch (ServiceResultException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateActions() {
        SessionElement sessionElement = manager.getSessions().get(0);
        actions.forEach(action -> action.readActions(sessionElement));
    }

    private void write(Variant value, String node) {
        ThreadWrite t;

        SessionElement sessionElement = manager.getSessions().get(0);
        t = new ThreadWrite(sessionElement.getSession(), namespace, node, Attributes.Value, value);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                WriteResponse res = (WriteResponse) msg.obj;
                String response = res.getResults()[0].getDescription();
                System.out.println("values sent " + response);
            }
        };
        t.start(handler);
    }

    @Override
    public Variant getStatus() {
        return status;
    }

    @Override
    public List<Action> getActions() {
        return actions;
    }

    @Override
    public String getType() {
        return "Światła";
    }

    @Override
    public String getRoom() {
        return room;
    }

    @Override
    public String getNodeId() {
        return nodeId;
    }

    @Override
    public int getTurnedOnImage() {
        return R.drawable.ic_light_on;
    }

    @Override
    public int getTurnedOffImage() {
        return R.drawable.ic_light_off;
    }
}
