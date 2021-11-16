package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.utils.DevicesUtils;

public class FloorActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private LinearLayout firstFloor;
    private LinearLayout groundFloor;
    private RelativeLayout gate;
    private ImageView deviceStatus;
    private ImageView chosenDevice;
    AlertDialog dialog;

    DevicesUtils devices;
    private boolean isActivityVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        firstFloor = findViewById(R.id.first_floor);
        groundFloor = findViewById(R.id.ground_floor);
        drawerLayout = findViewById(R.id.drawerLayout);
        gate = findViewById(R.id.gate);
        devices = new DevicesUtils(this);
        isActivityVisible = true;
        devices.updateStatus();
    }


    public void ClickMenu(View view) {
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickFloor(View view) {
        MainActivity.redirectActivity(this, FloorActivity.class);
    }

    public void ClickRoom(View view) {
        MainActivity.redirectActivity(this, RoomActivity.class);
    }

    public void ClickLight(View view) {
        MainActivity.redirectActivity(this, LightActivity.class);
    }

    public void ClickOPCUA(View view) {
        MainActivity.redirectActivity(this, OpcuaActivity.class);
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

    public void handleLightClick(View view) {
        chosenDevice = (ImageView) view;
        createCustomDialog(R.layout.popup_light);
    }

    public void handleSocketClick(View view) {
        chosenDevice = (ImageView) view;
        createCustomDialog(R.layout.popup_socket);
    }

    public void handleSunblindClick(View view) {
        chosenDevice = (ImageView) view;
        createCustomDialog(R.layout.popup_sunblind);
    }

    public void handleSensorClick(View view) {
        chosenDevice = (ImageView) view;
        createCustomDialog(R.layout.popup_sensor);
    }

    public void handleGateClick(View view) {
        chosenDevice = (ImageView) view;
        createCustomDialog(R.layout.popup_gate);
    }

    public void changeImage(int resId) {
        if (dialog != null && dialog.isShowing()) {
            deviceStatus.setImageResource(resId);
        }
    }

    public void handleTurnOn(View v) {
        devices.getDevices().get(chosenDevice).turnOn();
    }

    public void handleTurnOff(View v) {
        devices.getDevices().get(chosenDevice).turnOff();
    }

    private void createCustomDialog(int layout) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(layout, null);
        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
        deviceStatus = dialog.findViewById(R.id.deviceStatus);
    }

    @Override
    public void onResume() {
        super.onResume();
        isActivityVisible = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isActivityVisible = false;
    }

    public ImageView getChosenDevice() {
        return chosenDevice;
    }

    public boolean isActivityVisible() {
        return isActivityVisible;
    }
}