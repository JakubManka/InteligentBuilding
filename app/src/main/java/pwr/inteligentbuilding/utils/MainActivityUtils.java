package pwr.inteligentbuilding.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import org.opcfoundation.ua.builtintypes.Variant;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import pwr.inteligentbuilding.adapter.ActionBaseAdapter;
import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.activity.MainActivity;
import pwr.inteligentbuilding.model.Action;
import pwr.inteligentbuilding.model.Device;
import pwr.inteligentbuilding.model.Devices;

public class MainActivityUtils {

    private final MainActivity activity;
    private final Devices devices;
    private ImageView deviceStatus;
    private AlertDialog dialog;
    ListView actionsView;

    public MainActivityUtils(MainActivity activity, Devices devices) {
        this.activity = activity;
        this.devices = devices;
    }

    public void updateView() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(1000);
                        if(activity.isActivityVisible()){
                            activity.runOnUiThread(() -> updateImages());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    public void createCustomDialog(int layout) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        final View contactPopupView = activity.getLayoutInflater().inflate(layout, null);

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        setDeviceStatusImage(activity.getChosenDevice().getTag().toString());
        devices.setChosenDevice(activity.getChosenDevice());
        updateAdapter();
    }

    private void updateAdapter() {
        if (devices.getDevices().get(activity.getChosenDevice()) != null) {
            List<Action> actions = devices.getDevices().get(activity.getChosenDevice())
                    .getActions().stream().filter(a -> !a.getTriggerFunction().equals("")).collect(Collectors.toList());
            ActionBaseAdapter adapter = new ActionBaseAdapter(activity.getApplicationContext(), actions);
            actionsView = dialog.findViewById(R.id.actions);
            Objects.requireNonNull(actionsView).setAdapter(adapter);
        }
    }

    private void updateImages() {
        if (activity.isActivityVisible()) {
            devices.getDevices().forEach((key, value) -> {
                if (value.getStatus().toString().equals("true")) {
                    if (key.equals(activity.getChosenDevice())) {
                        setImage(value.getTurnedOnImage());
                        updateAdapter();
                    }
                    key.setImageResource(value.getTurnedOnImage());
                } else if (value.getStatus().toString().equals("false")) {
                    if (key.equals(activity.getChosenDevice())) {
                        setImage(value.getTurnedOffImage());
                    }
                    key.setImageResource(value.getTurnedOffImage());
                    updateAdapter();
                }
            });
        }
    }


    private void setImage(int resId) {
        if (dialog != null && dialog.isShowing()) {
            deviceStatus.setImageResource(resId);
        }
    }

    private void setDeviceStatusImage(String tag) {
        deviceStatus = dialog.findViewById(R.id.deviceStatus);
        Device device = devices.getDevices().get(activity.getChosenDevice());
        Variant statusTrue = new Variant(true);
        if (tag.equals(activity.getString(R.string.light))) {
            if (Objects.equals(device.getStatus(), statusTrue)) {
                deviceStatus.setImageResource(R.drawable.ic_light_on);
            } else {
                deviceStatus.setImageResource(R.drawable.ic_light_off);
            }

        } else if (tag.equals(activity.getString(R.string.socket))) {
            if (Objects.equals(device.getStatus(), statusTrue)) {
                deviceStatus.setImageResource(R.drawable.ic_socket_on);
            } else {
                deviceStatus.setImageResource(R.drawable.ic_socket_off);
            }

        } else if (tag.equals(activity.getString(R.string.sensor))) {
            if (Objects.equals(device.getStatus(), statusTrue)) {
                deviceStatus.setImageResource(R.drawable.ic_sensor_on);
            } else {
                deviceStatus.setImageResource(R.drawable.ic_sensor_off);
            }

        } else if (tag.equals(activity.getString(R.string.sunblind))) {
            if (Objects.equals(device.getStatus(), statusTrue)) {
                deviceStatus.setImageResource(R.drawable.ic_sunblind_on);
            } else {
                deviceStatus.setImageResource(R.drawable.ic_sunblind_off);
            }

        } else if (tag.equals(activity.getString(R.string.gate))) {
            if (Objects.equals(device.getStatus(), statusTrue)) {
                deviceStatus.setImageResource(R.drawable.ic_gate_on);
            } else {
                deviceStatus.setImageResource(R.drawable.ic_gate_off);
            }
        }
    }
}
