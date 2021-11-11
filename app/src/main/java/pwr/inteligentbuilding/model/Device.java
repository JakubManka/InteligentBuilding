package pwr.inteligentbuilding.model;

import org.opcfoundation.ua.builtintypes.Variant;

public interface Device {

    public void turnOn();
    public void turnOff();
    public void updateStatus();
    public Variant getStatus();
}
