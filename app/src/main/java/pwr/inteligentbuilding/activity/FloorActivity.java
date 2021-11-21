package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.utils.DevicesUtils;

public class FloorActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private LinearLayout firstFloor;
    private LinearLayout groundFloor;
    private RelativeLayout gate;
    private ImageView deviceStatus;
    private ImageView chosenDevice;
    private AlertDialog dialog;
    private DevicesUtils devices;
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

    public void handleEditAction(View v) {
        MainActivity.redirectActivity(this, EditActionsActivity.class);
    }

    private void createCustomDialog(int layout) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(layout, null);
        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
        deviceStatus = dialog.findViewById(R.id.deviceStatus);
        ListView actionsView = dialog.findViewById(R.id.actions);
        ArrayAdapter<String> actionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 ,
                devices.getDevices().get(chosenDevice).getActions());
        actionsView.setAdapter(actionsAdapter);

        ArrayAdapter<String> optionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.actionOptions));
        optionsAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
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