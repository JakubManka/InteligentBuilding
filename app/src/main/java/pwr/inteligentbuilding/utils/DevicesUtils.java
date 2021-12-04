package pwr.inteligentbuilding.utils;

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


import android.app.Activity;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.activity.FloorActivity;
import pwr.inteligentbuilding.model.Device;
import pwr.inteligentbuilding.model.Light;

public class DevicesUtils implements Serializable {
    private final Map<ImageView, Device> devices;
    private FloorActivity activity;
    int i = 0;

    public DevicesUtils() {
        devices = new HashMap<>();
    }

    public Map<ImageView, Device> getDevices() {
//        System.out.println(i+1);
//        i++;
        return devices;
    }

    public void setupDevices(FloorActivity activity) {
        System.out.println("set devices" + this);
        this.activity = activity;
        int namespace = 4;
        devices.put(activity.findViewById(R.id.light_1), new Light(OS_GABINET, namespace));
        devices.put(activity.findViewById(R.id.light_2), new Light(OS_SCHODY, namespace));
        devices.put(activity.findViewById(R.id.light_3), new Light(OS_KOTLOWNIA, namespace));
        devices.put(activity.findViewById(R.id.light_4), new Light(OS_NOCNE_GABINET, namespace));
        devices.put(activity.findViewById(R.id.light_5), new Light(OS_GARAZ, namespace));
        devices.put(activity.findViewById(R.id.light_6), new Light(OS_LAZIENKA_DOL_GORNE, namespace));
        devices.put(activity.findViewById(R.id.light_7), new Light(OS_KUCHNIA, namespace));
        devices.put(activity.findViewById(R.id.light_8), new Light(OS_SALON_1, namespace));
        devices.put(activity.findViewById(R.id.light_9), new Light(OS_SALON_2, namespace));
        devices.put(activity.findViewById(R.id.light_10), new Light(OS_SALON_3, namespace));
        devices.put(activity.findViewById(R.id.light_11), new Light(OS_TV, namespace));
//        devices.put(activity.findViewById(R.id.socket_1), new Socket("2", namespace));
//        devices.put(activity.findViewById(R.id.socket_2), new Socket("2", namespace));
//        devices.put(activity.findViewById(R.id.socket_3), new Socket("2", namespace));
//        devices.put(activity.findViewById(R.id.socket_4), new Socket("2", namespace));
//        devices.put(activity.findViewById(R.id.socket_5), new Socket("2", namespace));
//        devices.put(activity.findViewById(R.id.socket_6), new Socket("2", namespace));
//        devices.put(activity.findViewById(R.id.sensor_1), new Sensor("2", namespace));
//        devices.put(activity.findViewById(R.id.sensor_2), new Sensor("2", namespace));
//        devices.put(activity.findViewById(R.id.sensor_3), new Sensor("2", namespace));
//        devices.put(activity.findViewById(R.id.sensor_4), new Sensor("2", namespace));
//        devices.put(activity.findViewById(R.id.sensor_5), new Sensor("2", namespace));
//        devices.put(activity.findViewById(R.id.sunblind_1), new Sunblind("2", namespace));
//        devices.put(activity.findViewById(R.id.sunblind_2), new Sunblind("2", namespace));
//        devices.put(activity.findViewById(R.id.sunblind_3), new Sunblind("2", namespace));
//        devices.put(activity.findViewById(R.id.sunblind_4), new Sunblind("2", namespace));
//        devices.put(activity.findViewById(R.id.sunblind_5), new Sunblind("2", namespace));
//        devices.put(activity.findViewById(R.id.sunblind_6), new Sunblind("2", namespace));
//        devices.put(activity.findViewById(R.id.sunblind_7), new Sunblind("2", namespace));
//        devices.put(activity.findViewById(R.id.sunblind_8), new Sunblind("2", namespace));
//        devices.put(activity.findViewById(R.id.gate), new Gate("2", namespace));
    }

    public void updateImages() {
        devices.forEach((key, value) -> {
            if (value.getStatus().toString().equals("true")) {
                if (key.equals(activity.getChosenDevice())) {
                    activity.changeImage(value.getTurnedOnImage());
                }
                key.setImageResource(value.getTurnedOnImage());
            } else if (value.getStatus().toString().equals("false")) {
                if (key.equals(activity.getChosenDevice())) {
                    activity.changeImage(value.getTurnedOffImage());
                }
                key.setImageResource(value.getTurnedOffImage());
            }
        });
    }

    public FloorActivity getActivity() {
        return activity;
    }

    public void setActivity(FloorActivity activity) {
        this.activity = activity;
    }


}
