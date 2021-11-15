package pwr.inteligentbuilding.model;

import static pwr.inteligentbuilding.utils.RequestName.*;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.ReadResponse;
import org.opcfoundation.ua.core.TimestampsToReturn;
import org.opcfoundation.ua.core.WriteResponse;

import pwr.inteligentbuilding.OpcUtils.ConnectionThread.ThreadRead;
import pwr.inteligentbuilding.OpcUtils.ConnectionThread.ThreadWrite;
import pwr.inteligentbuilding.OpcUtils.ManagerOPC;
import pwr.inteligentbuilding.OpcUtils.SessionElement;

public class Light implements Device {
    private final String nodeId;
    private final int namespace;
    private final ManagerOPC manager;
    private Variant status;

    public Light(String nodeId, int namespace) {
        this.nodeId = nodeId;
        this.namespace = namespace;
        this.manager = ManagerOPC.getIstance();
        status = new Variant("undefined");
    }

    @Override
    public void turnOn() {
        Variant value_write = new Variant("true");
        write(value_write, nodeId + REQ_ON);
    }

    @Override
    public void turnOff() {
        Variant value_write = new Variant("0");
        write(value_write, nodeId + REQ_OFF);
    }

    @Override
    public void updateStatus() {
        SessionElement sessionElement = manager.getSessions().get(0);
        ThreadRead t = new ThreadRead(sessionElement.getSession(), 0, TimestampsToReturn.Both, namespace, nodeId + IS_ON, Attributes.Value);
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ReadResponse res = (ReadResponse) msg.obj;
                System.out.println(res.getResults()[0].getValue());
                status = res.getResults()[0].getValue();

            }
        };
        t.start(handler);
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
}
