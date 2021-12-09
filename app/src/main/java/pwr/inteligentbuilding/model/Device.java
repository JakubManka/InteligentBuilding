package pwr.inteligentbuilding.model;

import org.opcfoundation.ua.builtintypes.Variant;

import java.io.Serializable;
import java.util.List;

public interface Device extends Serializable {

    void turnOn();
    void turnOff();
    void updateStatus();
    void updateActions();
    void setActions();
    Variant getStatus();
    List<Action> getActions();
    String getType();
    String getRoom();
    String getNodeId();
    int getTurnedOnImage();
    int getTurnedOffImage();
}
