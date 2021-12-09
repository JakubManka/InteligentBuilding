package pwr.inteligentbuilding.model;

import static pwr.inteligentbuilding.utils.LightName.OS_GABINET;
import static pwr.inteligentbuilding.utils.LightName.OS_GARAZ;
import static pwr.inteligentbuilding.utils.LightName.OS_KOTLOWNIA;
import static pwr.inteligentbuilding.utils.LightName.OS_KUCHNIA;
import static pwr.inteligentbuilding.utils.LightName.OS_LAZIENKA_DOL_GORNE;
import static pwr.inteligentbuilding.utils.LightName.OS_NOCNE_GABINET;
import static pwr.inteligentbuilding.utils.LightName.OS_SALON_1;
import static pwr.inteligentbuilding.utils.LightName.OS_SALON_2;
import static pwr.inteligentbuilding.utils.LightName.OS_SALON_3;
import static pwr.inteligentbuilding.utils.LightName.OS_SCHODY;
import static pwr.inteligentbuilding.utils.LightName.OS_TV;


import android.widget.ImageView;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.activity.MainActivity;
import pwr.inteligentbuilding.utils.DevicesUtils;

public class Devices implements Serializable {
    private final Map<ImageView, Device> devices;
    private Device chosenDevice;

    public Devices() {
        devices = new HashMap<>();
    }

    public void setupDevices(MainActivity activity) {
        List<Device> deviceList = DevicesUtils.getDevices(activity);
        devices.put(activity.findViewById(R.id.light_1),deviceList.get(0));
        devices.put(activity.findViewById(R.id.light_2),deviceList.get(1));
        devices.put(activity.findViewById(R.id.light_3),deviceList.get(2));
        devices.put(activity.findViewById(R.id.light_4),deviceList.get(3));
        devices.put(activity.findViewById(R.id.light_5),deviceList.get(4));
        devices.put(activity.findViewById(R.id.light_6),deviceList.get(5));
        devices.put(activity.findViewById(R.id.light_7),deviceList.get(6));
        devices.put(activity.findViewById(R.id.light_8),deviceList.get(7));
        devices.put(activity.findViewById(R.id.light_9),deviceList.get(8));
        devices.put(activity.findViewById(R.id.light_10),deviceList.get(9));
        devices.put(activity.findViewById(R.id.light_11),deviceList.get(10));

//        devices.put(activity.findViewById(R.id.socket_1), new Socket("2", namespace, ));
//        devices.put(activity.findViewById(R.id.socket_2), new Socket("2", namespace, ));
//        devices.put(activity.findViewById(R.id.socket_3), new Socket("2", namespace, ));
//        devices.put(activity.findViewById(R.id.socket_4), new Socket("2", namespace, ));
//        devices.put(activity.findViewById(R.id.socket_5), new Socket("2", namespace, ));
//        devices.put(activity.findViewById(R.id.socket_6), new Socket("2", namespace, ));
//        devices.put(activity.findViewById(R.id.sensor_1), new Sensor("2", namespace, ));
//        devices.put(activity.findViewById(R.id.sensor_2), new Sensor("2", namespace, ));
//        devices.put(activity.findViewById(R.id.sensor_3), new Sensor("2", namespace, ));
//        devices.put(activity.findViewById(R.id.sensor_4), new Sensor("2", namespace, ));
//        devices.put(activity.findViewById(R.id.sensor_5), new Sensor("2", namespace, ));
//        devices.put(activity.findViewById(R.id.sunblind_1), new Sunblind("2", namespace, ));
//        devices.put(activity.findViewById(R.id.sunblind_2), new Sunblind("2", namespace, ));
//        devices.put(activity.findViewById(R.id.sunblind_3), new Sunblind("2", namespace, ));
//        devices.put(activity.findViewById(R.id.sunblind_4), new Sunblind("2", namespace, ));
//        devices.put(activity.findViewById(R.id.sunblind_5), new Sunblind("2", namespace, ));
//        devices.put(activity.findViewById(R.id.sunblind_6), new Sunblind("2", namespace, ));
//        devices.put(activity.findViewById(R.id.sunblind_7), new Sunblind("2", namespace, ));
//        devices.put(activity.findViewById(R.id.sunblind_8), new Sunblind("2", namespace, ));
//        devices.put(activity.findViewById(R.id.gate), new Gate("2", namespace, ));
    }

    public Device getChosenDevice() {
        return chosenDevice;
    }

    public void setChosenDevice(ImageView chosenDevice) {
        this.chosenDevice = devices.get(chosenDevice);
    }

    public Map<ImageView, Device> getDevices() {
        return devices;
    }
}
