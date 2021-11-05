package pwr.inteligentbuilding.utils;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.activity.FloorActivity;
import pwr.inteligentbuilding.model.Device;
import pwr.inteligentbuilding.model.Gate;
import pwr.inteligentbuilding.model.Light;
import pwr.inteligentbuilding.model.Sensor;
import pwr.inteligentbuilding.model.Socket;
import pwr.inteligentbuilding.model.Sunblind;

public class DevicesUtils {
    private final Map<View, Device> devices;
    private final FloorActivity activity;

    public DevicesUtils(FloorActivity activity) {
        this.activity = activity;
        devices = new HashMap<>();
    }

    public Map<View, Device> getDevices() {
        return devices;
    }

    public void setupDevices(){
        int namespace = 2;

        devices.put(activity.findViewById(R.id.light_1), new Light("2",namespace));
        devices.put(activity.findViewById(R.id.light_2), new Light("2",namespace));
        devices.put(activity.findViewById(R.id.light_3), new Light("2",namespace));
        devices.put(activity.findViewById(R.id.light_4), new Light("2",namespace));
        devices.put(activity.findViewById(R.id.light_5), new Light("2",namespace));
        devices.put(activity.findViewById(R.id.light_6), new Light("2",namespace));
        devices.put(activity.findViewById(R.id.light_7), new Light("2",namespace));
        devices.put(activity.findViewById(R.id.light_8), new Light("2",namespace));
        devices.put(activity.findViewById(R.id.light_9), new Light("2",namespace));
        devices.put(activity.findViewById(R.id.light_10), new Light("2",namespace));
        devices.put(activity.findViewById(R.id.light_11), new Light("2",namespace));
        devices.put(activity.findViewById(R.id.socket_1), new Socket("2",namespace));
        devices.put(activity.findViewById(R.id.socket_2), new Socket("2",namespace));
        devices.put(activity.findViewById(R.id.socket_3), new Socket("2",namespace));
        devices.put(activity.findViewById(R.id.socket_4), new Socket("2",namespace));
        devices.put(activity.findViewById(R.id.socket_5), new Socket("2",namespace));
        devices.put(activity.findViewById(R.id.socket_6), new Socket("2",namespace));
        devices.put(activity.findViewById(R.id.sensor_1), new Sensor("2",namespace));
        devices.put(activity.findViewById(R.id.sensor_2), new Sensor("2",namespace));
        devices.put(activity.findViewById(R.id.sensor_3), new Sensor("2",namespace));
        devices.put(activity.findViewById(R.id.sensor_4), new Sensor("2",namespace));
        devices.put(activity.findViewById(R.id.sensor_5), new Sensor("2",namespace));
        devices.put(activity.findViewById(R.id.sunblind_1), new Sunblind("2",namespace));
        devices.put(activity.findViewById(R.id.sunblind_2), new Sunblind("2",namespace));
        devices.put(activity.findViewById(R.id.sunblind_3), new Sunblind("2",namespace));
        devices.put(activity.findViewById(R.id.sunblind_4), new Sunblind("2",namespace));
        devices.put(activity.findViewById(R.id.sunblind_5), new Sunblind("2",namespace));
        devices.put(activity.findViewById(R.id.sunblind_6), new Sunblind("2",namespace));
        devices.put(activity.findViewById(R.id.sunblind_7), new Sunblind("2",namespace));
        devices.put(activity.findViewById(R.id.sunblind_8), new Sunblind("2",namespace));
        devices.put(activity.findViewById(R.id.gate), new Gate("2",namespace));
    }
}
