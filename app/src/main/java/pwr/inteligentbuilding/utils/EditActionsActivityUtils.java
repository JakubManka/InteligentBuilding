package pwr.inteligentbuilding.utils;

import java.util.List;

import pwr.inteligentbuilding.activity.EditActionsActivity;
import pwr.inteligentbuilding.model.Device;

public class EditActionsActivityUtils {
    private final List<Device> devices;
    private final EditActionsActivity activity;

    public EditActionsActivityUtils(EditActionsActivity activity) {
        this.activity = activity;
        devices = DevicesUtils.getDevices(this.activity);
        updateStatus();
    }

    public void updateStatus() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                devices.forEach(Device::updateStatus);
            }
        };
        thread.start();
    }

    public List<Device> getDevices() {
        return devices;
    }
}
