package pwr.inteligentbuilding.model;

import org.opcfoundation.ua.builtintypes.Variant;

import java.util.ArrayList;
import java.util.List;

import pwr.inteligentbuilding.utils.opcUtils.ManagerOPC;

public class Gate implements Device {
    private final String nodeId;
    private final int namespace;
    private final ManagerOPC manager;
    private Variant status;
    private final List<Action> actions;
    private final String room;

    public Gate(String nodeId, int namespace, String room) {
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
    public void updateActions() {

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
        return "Brama";
    }

    @Override
    public String getRoom() {
        return room;
    }

    @Override
    public String getNodeId() {
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
