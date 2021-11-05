package pwr.inteligentbuilding.model;

import pwr.inteligentbuilding.OpcUtils.ManagerOPC;

public class Gate implements Device {
    String nodeId;
    int namespace;
    ManagerOPC manager;

    public Gate(String nodeId, int namespace) {
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
}
