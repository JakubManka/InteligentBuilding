package pwr.inteligentbuilding.model;

import org.opcfoundation.ua.builtintypes.Variant;

import pwr.inteligentbuilding.OpcUtils.ManagerOPC;

public class Socket implements Device {
    String nodeId;
    int namespace;
    ManagerOPC manager;

    public Socket(String nodeId, int namespace) {
        this.nodeId = nodeId;
        this.namespace = namespace;
        manager = ManagerOPC.getIstance();
    }

    @Override
    public void turnOn() {

    }

    @Override
    public void turnOff() {

    }

    @Override
    public void updateStatus() {

    }

    @Override
    public Variant getStatus() {
        return null;
    }
}
