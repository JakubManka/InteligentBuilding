package pwr.inteligentbuilding.utils;

import static org.opcfoundation.ua.utils.EndpointUtil.selectByProtocol;
import static org.opcfoundation.ua.utils.EndpointUtil.sortBySecurityLevel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.core.EndpointDescription;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pwr.inteligentbuilding.activity.MainActivity;
import pwr.inteligentbuilding.model.Device;
import pwr.inteligentbuilding.model.Devices;
import pwr.inteligentbuilding.utils.opcUtils.ConnectionThread.ThreadCreateSession;
import pwr.inteligentbuilding.utils.opcUtils.ConnectionThread.ThreadDiscoveryEndpoints;
import pwr.inteligentbuilding.utils.opcUtils.ManagerOPC;

public class OpcuaConnection {
    private final ManagerOPC manager;
    private final MainActivity activity;
    private final List<EndpointDescription> endpoints_list;
    private EndpointDescription[] endpoints;
    private String url;
    private final Devices deviceModel;
    private Map<ImageView, Device> devices;


    public OpcuaConnection(MainActivity activity, File filePath, Devices deviceModel) {
        this.activity = activity;
        this.deviceModel = deviceModel;
        File certFile = new File(filePath, "OPCCert.der");
        File privKeyFile = new File(filePath, "OPCCert.pem");
        manager = ManagerOPC.CreateManagerOPC(certFile, privKeyFile);
        endpoints_list = new ArrayList<>();
        devices = new HashMap<>();
    }

    public void connect() {
        url = "opc.tcp://10.1.1.5:4840";
        Client client = manager.getClient();
        ThreadDiscoveryEndpoints t = new ThreadDiscoveryEndpoints(client, url);

        @SuppressLint("HandlerLeak") Handler handler_discovery = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                endpoints = selectByProtocol(sortBySecurityLevel((EndpointDescription[]) msg.obj), "opc.tcp");
                endpoints_list.addAll(Arrays.asList(endpoints));

                createSession();
            }
        };
        t.start(handler_discovery);
    }

    private void createSession() {
        ThreadCreateSession t = new ThreadCreateSession(manager, url, endpoints[0]);
        ProgressDialog dialog = ProgressDialog.show(activity, "connection attempt", "Session creation", true);
        t.start();
        dialog.dismiss();
        updateStatus();
    }

    private void updateStatus() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true){
                        sleep(1000);
                        devices = deviceModel.getDevices();
                        if (!devices.isEmpty()) {
                            devices.values().forEach(Device::updateStatus);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
