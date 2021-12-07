package pwr.inteligentbuilding.model;

import org.opcfoundation.ua.builtintypes.Variant;

import java.util.ArrayList;
import java.util.List;

import pwr.inteligentbuilding.utils.opcUtils.ManagerOPC;

public class Sunblind implements Device {
    private final String nodeId;
    private final int namespace;
    private final ManagerOPC manager;
    private Variant status;
    private final List<Action> actions;
    private final String room;

    public Sunblind(String nodeId, int namespace, String room) {
        this.nodeId = nodeId;
        this.namespace = namespace;
        this.room = room;
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
    public void setActions() {

    }

    @Override
    public Variant getStatus() {
        return null;
    }

    @Override
    public List<Action> getActions() {
        return actions;
    }

    @Override
    public String getType() {
        return "Żaluzje";
    }

    @Override
    public String getRoom() {
        return room;
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
