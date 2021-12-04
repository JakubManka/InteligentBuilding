package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.opcfoundation.ua.builtintypes.Variant;

import java.util.Objects;

import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.model.Device;
import pwr.inteligentbuilding.utils.DevicesUtils;
import pwr.inteligentbuilding.utils.Drawer;
import pwr.inteligentbuilding.utils.OpcuaConnection;

public class FloorActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private LinearLayout firstFloor;
    private LinearLayout groundFloor;
    private RelativeLayout gate;
    private ImageView deviceStatus;
    private ImageView chosenDevice;
    private AlertDialog dialog;
    private DevicesUtils devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        DevicesUtils devices = new DevicesUtils();
        OpcuaConnection opcua = new OpcuaConnection(this, getFilesDir(), devices);
        opcua.connect();

        firstFloor = findViewById(R.id.first_floor);
        groundFloor = findViewById(R.id.ground_floor);
        drawerLayout = findViewById(R.id.drawerLayout);
        gate = findViewById(R.id.gate);
        devices.setupDevices(this);
        images();
    }

    public void ClickMenu(View view) {
        Drawer.openDrawer(drawerLayout);
    }

    public void ClickFloor(View view) {
        Drawer.redirectActivity(this, FloorActivity.class);
    }

    public void ClickOPCUA(View view) {
        Drawer.redirectActivity(this, OpcuaActivity.class);
    }

    public void handleFirstFloorClick(View view) {
        this.gate.setVisibility(View.GONE);
        groundFloor.setVisibility(View.GONE);
        firstFloor.setVisibility(View.VISIBLE);
    }

    public void handleGroundFloorClick(View view) {
        this.gate.setVisibility(View.VISIBLE);
        firstFloor.setVisibility(View.GONE);
        groundFloor.setVisibility(View.VISIBLE);
    }

    public void handleImageClick(View view) {
        chosenDevice = (ImageView) view;
        createCustomDialog(R.layout.popup);
    }

    public void changeImage(int resId) {
        if (dialog != null && dialog.isShowing()) {
            deviceStatus.setImageResource(resId);
        }
    }

    private void images() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while(true){
                    try {
                        sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                devices.updateImages();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    public void updateImage(String tag) {
        deviceStatus = dialog.findViewById(R.id.deviceStatus);
        Device device = devices.getDevices().get(chosenDevice);
        Variant statusTrue = new Variant(true);
        if (tag.equals(getString(R.string.light))) {
            if (Objects.equals(device.getStatus(), statusTrue)) {
                deviceStatus.setImageResource(R.drawable.ic_light_on);
            } else {
                deviceStatus.setImageResource(R.drawable.ic_light_off);
            }

        } else if (tag.equals(getString(R.string.socket))) {
            if (Objects.equals(device.getStatus(), statusTrue)) {
                deviceStatus.setImageResource(R.drawable.ic_socket_on);
            } else {
                deviceStatus.setImageResource(R.drawable.ic_socket_off);
            }

        } else if (tag.equals(getString(R.string.sensor))) {
            if (Objects.equals(device.getStatus(), statusTrue)) {
                deviceStatus.setImageResource(R.drawable.ic_sensor_on);
            } else {
                deviceStatus.setImageResource(R.drawable.ic_sensor_off);
            }

        } else if (tag.equals(getString(R.string.sunblind))) {
            if (Objects.equals(device.getStatus(), statusTrue)) {
                deviceStatus.setImageResource(R.drawable.ic_sunblind_on);
            } else {
                deviceStatus.setImageResource(R.drawable.ic_sunblind_off);
            }

        } else if (tag.equals(getString(R.string.gate))) {
            if (Objects.equals(device.getStatus(), statusTrue)) {
                deviceStatus.setImageResource(R.drawable.ic_gate_on);
            } else {
                deviceStatus.setImageResource(R.drawable.ic_gate_off);
            }

        }
    }

    public void handleTurnOn(View v) {
        devices.getDevices().get(chosenDevice).turnOn();
    }

    public void handleTurnOff(View v) {
        devices.getDevices().get(chosenDevice).turnOff();
    }

    public void handleEditAction(View v) {
        Drawer.redirectActivity(this, EditActionsActivity.class);
    }

    private void createCustomDialog(int layout) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(layout, null);
        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
        updateImage(chosenDevice.getTag().toString());
        ListView actionsView = dialog.findViewById(R.id.actions);
//        ArrayAdapter<String> actionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 ,
//                devices.getDevices().get(chosenDevice).getActions());
//        actionsView.setAdapter(actionsAdapter);

        ArrayAdapter<String> optionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.actionOptions));
        optionsAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public ImageView getChosenDevice() {
        return chosenDevice;
    }
}