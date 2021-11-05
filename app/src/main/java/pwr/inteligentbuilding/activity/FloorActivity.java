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

import java.util.HashMap;
import java.util.Map;

import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.model.Device;
import pwr.inteligentbuilding.model.Light;
import pwr.inteligentbuilding.utils.DevicesUtils;

public class FloorActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private LinearLayout firstFloor;
    private LinearLayout groundFloor;
    private RelativeLayout gate;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private ImageView deviceStatus;
    private ImageView chosenView;

    DevicesUtils devices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        chosenView = findViewById(R.id.light_2);
        firstFloor = findViewById(R.id.first_floor);
        groundFloor = findViewById(R.id.ground_floor);
        drawerLayout = findViewById(R.id.drawerLayout);
        gate = findViewById(R.id.gate);
        devices = new DevicesUtils(this);
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

    public void ClickOPCUA(View view){
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
        chosenView =(ImageView) view;
        createCustomDialog(R.layout.popup_light);
    }

    public void handleSocketClick(View view) {
        chosenView =(ImageView) view;
        createCustomDialog(R.layout.popup_socket);
    }

    public void handleSunblindClick(View view) {
        chosenView =(ImageView) view;
        createCustomDialog(R.layout.popup_sunblind);
    }

    public void handleSensorClick(View view) {
        chosenView =(ImageView) view;
        createCustomDialog(R.layout.popup_sensor);
    }

    public void handleGateClick(View view) {
        chosenView =(ImageView) view;
        createCustomDialog(R.layout.popup_gate);
    }

    public void changeImage(int resId){
        chosenView.setImageResource(resId);
        deviceStatus.setImageResource(resId);
    }

    public void handleTurnOn(View v){
//        devices.getDevices().get(chosenView).updateStatus();
        System.out.println(deviceStatus.getTag());
        if(deviceStatus.getTag().equals("light")){
            changeImage(R.drawable.ic_light_on);
        }

    }

    public void handleTurnOff(View v){
        if(deviceStatus.getTag().equals("light")){
            changeImage(R.drawable.ic_light_off);
        }
//        devices.getDevices().get(chosenView).turnOn();
    }

    private void createCustomDialog(int layout){
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(layout, null);
        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
        deviceStatus =  dialog.findViewById(R.id.deviceStatus);
    }
}