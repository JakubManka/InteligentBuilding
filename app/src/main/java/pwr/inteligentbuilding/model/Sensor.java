package pwr.inteligentbuilding.model;

import org.opcfoundation.ua.builtintypes.Variant;

import java.util.ArrayList;
import java.util.List;

import pwr.inteligentbuilding.utils.opcUtils.ManagerOPC;

public class Sensor implements Device {
    private final String nodeId;
    private final int namespace;
    private final ManagerOPC manager;
    private Variant status;
    private final List<oneAction> actions;

    public Sensor(String nodeId, int namespace) {
        this.nodeId = nodeId;
        this.namespace = namespace;
        this.manager = ManagerOPC.getIstance();
        status = new Variant("undefined");
        actions = new ArrayList<>();
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
    public List<oneAction> getActions() {
        return actions;
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
