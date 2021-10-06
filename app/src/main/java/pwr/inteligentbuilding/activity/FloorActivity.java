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
    RelativeLayout gate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        firstFloor = findViewById(R.id.first_floor);
        groundFloor = findViewById(R.id.ground_floor);
        drawerLayout = findViewById(R.id.drawerLayout);
        gate = findViewById(R.id.gate);
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
        gate.setVisibility(View.GONE);
        groundFloor.setVisibility(View.GONE);
        firstFloor.setVisibility(View.VISIBLE);
    }

    public void handleGroundFloorClick(View view) {
        gate.setVisibility(View.VISIBLE);
        firstFloor.setVisibility(View.GONE);
        groundFloor.setVisibility(View.VISIBLE);
    }

    public void handleLightClick(View view) {
        ImageView light = (ImageView) view;
        if (light.getTag() != null) {
            int tag = (int) light.getTag();
            if (tag == R.drawable.ic_light_off) {
                light.setImageResource(R.drawable.ic_light_on);
                light.setTag(R.drawable.ic_light_on);
            } else {
                light.setImageResource(R.drawable.ic_light_off);
                light.setTag(R.drawable.ic_light_off);
            }
        } else {
            light.setImageResource(R.drawable.ic_light_on);
            light.setTag(R.drawable.ic_light_on);
        }
    }

    public void handleSocketClick(View view) {
        ImageView socket = (ImageView) view;
        if (socket.getTag() != null) {
            int tag = (int) socket.getTag();
            if (tag == R.drawable.ic_socket_off) {
                socket.setImageResource(R.drawable.ic_socket_on);
                socket.setTag(R.drawable.ic_socket_on);
            } else {
                socket.setImageResource(R.drawable.ic_socket_off);
                socket.setTag(R.drawable.ic_socket_off);
            }
        } else {
            socket.setImageResource(R.drawable.ic_socket_on);
            socket.setTag(R.drawable.ic_socket_on);
        }
    }

    public void handleSunblindClick(View view) {
        ImageView sunblind = (ImageView) view;
        if (sunblind.getTag() != null) {
            int tag = (int) sunblind.getTag();
            if (tag == R.drawable.ic_sunblind_off) {
                sunblind.setImageResource(R.drawable.ic_sunblind_on);
                sunblind.setTag(R.drawable.ic_sunblind_on);
            } else {
                sunblind.setImageResource(R.drawable.ic_sunblind_off);
                sunblind.setTag(R.drawable.ic_sunblind_off);
            }
        } else {
            sunblind.setImageResource(R.drawable.ic_sunblind_on);
            sunblind.setTag(R.drawable.ic_sunblind_on);
        }
    }

    public void handleSensorClick(View view) {
        ImageView sensor = (ImageView) view;
        if (sensor.getTag() != null) {
            int tag = (int) sensor.getTag();
            if (tag == R.drawable.ic_sensor_off) {
                sensor.setImageResource(R.drawable.ic_sensor_on);
                sensor.setTag(R.drawable.ic_sensor_on);
            } else {
                sensor.setImageResource(R.drawable.ic_sensor_off);
                sensor.setTag(R.drawable.ic_sensor_off);
            }
        } else {
            sensor.setImageResource(R.drawable.ic_sensor_on);
            sensor.setTag(R.drawable.ic_sensor_on);
        }
    }

    public void handleGateClick(View view) {
        ImageView gate = (ImageView) view;
        if (gate.getTag() != null) {
            int tag = (int) gate.getTag();
            if (tag == R.drawable.ic_gate_off) {
                gate.setImageResource(R.drawable.ic_gate_on);
                gate.setTag(R.drawable.ic_gate_on);
            } else {
                gate.setImageResource(R.drawable.ic_gate_off);
                gate.setTag(R.drawable.ic_gate_off);
            }
        } else {
            gate.setImageResource(R.drawable.ic_gate_on);
            gate.setTag(R.drawable.ic_gate_on);
        }
    }
}