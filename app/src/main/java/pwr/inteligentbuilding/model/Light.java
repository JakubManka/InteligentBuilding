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

import pwr.inteligentbuilding.utils.opcUtils.ConnectionThread.ThreadWrite;
import pwr.inteligentbuilding.utils.opcUtils.ManagerOPC;
import pwr.inteligentbuilding.utils.opcUtils.SessionElement;
import pwr.inteligentbuilding.R;

public class Light implements Device {
    private final String nodeId;
    private final int namespace;
    private final ManagerOPC manager;
    private Variant status;
    private final List<oneAction> actions;

    public Light(String nodeId, int namespace) {
        this.nodeId = nodeId;
        this.namespace = namespace;
        this.manager = ManagerOPC.getIstance();
        status = new Variant("undefined");
        actions = new ArrayList<>();
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
    public List<oneAction> getActions() {
        return actions;
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
