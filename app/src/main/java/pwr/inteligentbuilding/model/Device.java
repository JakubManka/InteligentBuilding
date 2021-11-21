package pwr.inteligentbuilding.model;

import org.opcfoundation.ua.builtintypes.Variant;

import java.util.List;

public interface Device {

    public void turnOn();
    public void turnOff();
    public void updateStatus();
    public Variant getStatus();
    public List<String> getActions();
    public int getTurnedOnImage();
    public int getTurnedOffImage();
}
