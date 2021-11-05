package pwr.inteligentbuilding.model;

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
    String nodeId;
    int namespace;
    ManagerOPC manager;

    public Light(String nodeId, int namespace) {
        this.nodeId = nodeId;
        this.namespace = namespace;
        this.manager = ManagerOPC.getIstance();
    }

    @Override
    public void turnOn() {
        ThreadWrite t;

        Variant value_write = new Variant("3");
        SessionElement sessionElement = manager.getSessions().get(0);
        t = new ThreadWrite(sessionElement.getSession(), namespace, 2, Attributes.Value, value_write);

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
    public void turnOff() {

    }

    @Override
    public void updateStatus() {
        SessionElement sessionElement = manager.getSessions().get(0);
        ThreadRead t = new ThreadRead(sessionElement.getSession(), 0, TimestampsToReturn.Both, namespace, nodeId, Attributes.Value);
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ReadResponse res = (ReadResponse) msg.obj;
                System.out.println(res.getResults()[0].getValue());
            }
        };
        t.start(handler);
    }
}
