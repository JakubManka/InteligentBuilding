package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.model.Device;
import pwr.inteligentbuilding.model.Devices;
import pwr.inteligentbuilding.utils.Drawer;
import pwr.inteligentbuilding.utils.MainActivityUtils;
import pwr.inteligentbuilding.utils.OpcuaConnection;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private LinearLayout firstFloor;
    private LinearLayout groundFloor;
    private RelativeLayout gate;
    private ImageView chosenDevice;
    private Devices devices;
    private MainActivityUtils mainActivityUtils;
    private boolean isActivityVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        devices = new Devices();
        OpcuaConnection opcua = new OpcuaConnection(this, getFilesDir(), devices);
        opcua.connect();

        mainActivityUtils = new MainActivityUtils(this, devices);
        firstFloor = findViewById(R.id.first_floor);
        groundFloor = findViewById(R.id.ground_floor);
        drawerLayout = findViewById(R.id.drawerLayout);
        gate = findViewById(R.id.gate);
        isActivityVisible = true;

        devices.setupDevices(this);
        mainActivityUtils.updateView();
    }

    public void handleImageClick(View view) {
        chosenDevice = (ImageView) view;
        mainActivityUtils.createCustomDialog(R.layout.popup);
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

    public void handleTurnOn(View v) {
        devices.getDevices().get(chosenDevice).turnOn();
    }

    public void handleTurnOff(View v) {
        devices.getDevices().get(chosenDevice).turnOff();
    }

    public void handleEditAction(View v) {
        Drawer.redirectActivity(this, EditActionsActivity.class);
    }

    public void ClickMenu(View view) {
        Drawer.openDrawer(drawerLayout);
    }

    public void ClickFloor(View view) {
        Drawer.redirectActivity(this, MainActivity.class);
    }

    public void ClickActions(View view) {
        Drawer.redirectActivity(this, EditActionsActivity.class);
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