package pwr.inteligentbuilding.utils.opcUtils.ConnectionThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.EndpointDescription;

import pwr.inteligentbuilding.utils.opcUtils.ManagerOPC;
import pwr.inteligentbuilding.activity.FloorActivity;
import pwr.inteligentbuilding.activity.OpcuaActivity;


public class ThreadCreateSession extends Thread {

    private final ManagerOPC gestore;
    private final EndpointDescription endpointDescription;
    private final String url;
    private int position = -1;
    private boolean sent = false;

    public ThreadCreateSession(ManagerOPC gestore, String url, EndpointDescription endpointDescription) {
        this.gestore = gestore;
        this.endpointDescription = endpointDescription;
        this.url = url;
    }

    private synchronized void send(Message msg) {
        if (!sent) {
            msg.sendToTarget();
            sent = true;
        }
    }

    public void start() {
        super.start();
    }

    @Override
    public void run() {
        super.run();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        position = gestore.CreateSession(url, endpointDescription);
                    } catch (ServiceResultException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        try {
            t.join(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
