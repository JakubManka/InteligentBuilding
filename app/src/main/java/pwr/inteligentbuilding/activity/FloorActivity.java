package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import pwr.inteligentbuilding.R;

public class FloorActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    LinearLayout firstFloor;
    LinearLayout groundFloor;
    RelativeLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        firstFloor = findViewById(R.id.first_floor);
        groundFloor = findViewById(R.id.ground_floor);
        drawerLayout = findViewById(R.id.drawerLayout);
        view = findViewById(R.id.gate);
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
        this.view.setVisibility(View.GONE);
        groundFloor.setVisibility(View.GONE);
        firstFloor.setVisibility(View.VISIBLE);
    }

    public void handleGroundFloorClick(View view) {
        this.view.setVisibility(View.VISIBLE);
        firstFloor.setVisibility(View.GONE);
        groundFloor.setVisibility(View.VISIBLE);
    }

    public void handleLightClick(View view) {
        ImageView light = (ImageView) view;
        changeImage(light, R.drawable.ic_light_off, R.drawable.ic_light_on);
    }

    public void handleSocketClick(View view) {
        ImageView socket = (ImageView) view;
        changeImage(socket, R.drawable.ic_socket_off, R.drawable.ic_socket_on);
    }

    public void handleSunblindClick(View view) {
        ImageView sunblind = (ImageView) view;
        changeImage(sunblind, R.drawable.ic_sunblind_off, R.drawable.ic_sunblind_on);
    }

    public void handleSensorClick(View view) {
        ImageView sensor = (ImageView) view;
        changeImage(sensor, R.drawable.ic_sensor_off, R.drawable.ic_sensor_on);
    }

    public void handleGateClick(View view) {
        ImageView gate = (ImageView) view;
        changeImage(gate, R.drawable.ic_gate_off, R.drawable.ic_gate_on);
    }

    public void changeImage(ImageView view, int resIdOff, int resIdOn){
        if (view.getTag() != null) {
            int tag = (int) view.getTag();
            if (tag == resIdOff) {
                view.setImageResource(resIdOn);
                view.setTag(resIdOn);
            } else {
                view.setImageResource(resIdOff);
                view.setTag(resIdOff);
            }
        } else {
            view.setImageResource(resIdOn);
            view.setTag(resIdOn);
        }
    }
}