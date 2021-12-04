package pwr.inteligentbuilding.model;

import org.opcfoundation.ua.builtintypes.Variant;

import java.util.List;

public interface Device {

    void turnOn();
    void turnOff();
    void updateStatus();
    Variant getStatus();
    List<oneAction> getActions();
    int getTurnedOnImage();
    int getTurnedOffImage();
}
