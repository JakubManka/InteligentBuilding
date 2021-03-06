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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.model.Device;
import pwr.inteligentbuilding.model.Light;

public class DevicesUtils {

    public static List<Device> getDevices(Activity activity) {
        List<Device> devices = new ArrayList<>();
        List<String> lightNames = Arrays.asList(activity.getResources().getStringArray(R.array.lights));
        int namespace = 4;
        devices.add(new Light(OS_NOCNE_GABINET, namespace, lightNames.get(0)));
        devices.add(new Light(OS_SCHODY, namespace, lightNames.get(1)));
        devices.add(new Light(OS_KOTLOWNIA, namespace, lightNames.get(2)));
        devices.add(new Light(OS_GABINET, namespace, lightNames.get(3)));
        devices.add(new Light(OS_GARAZ, namespace, lightNames.get(4)));
        devices.add(new Light(OS_LAZIENKA_DOL_GORNE, namespace, lightNames.get(5)));
        devices.add(new Light(OS_KUCHNIA, namespace, lightNames.get(6)));
        devices.add(new Light(OS_SALON_1, namespace, lightNames.get(7)));
        devices.add(new Light(OS_SALON_2, namespace, lightNames.get(8)));
        devices.add(new Light(OS_SALON_3, namespace, lightNames.get(9)));
        devices.add(new Light(OS_TV, namespace, lightNames.get(10)));



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
        return devices;
    }
}
