package pwr.inteligentbuilding.model;

import org.opcfoundation.ua.builtintypes.Variant;

import java.util.List;

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

    @Override
    public Variant getStatus() {
        return null;
    }

    @Override
    public List<String> getActions() {
        return null;
    }

    @Override
    public int getTurnedOnImage() {
        return 0;
    }

    @Override
    public int getTurnedOffImage() {
        return 0;
    }
}
