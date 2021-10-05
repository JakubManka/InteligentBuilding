package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import pwr.inteligentbuilding.R;

public class FloorActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        drawerLayout = findViewById(R.id.drawerLayout);


    }
    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickFloor(View view){
        MainActivity.redirectActivity(this, FloorActivity.class);
    }

    public void ClickRoom(View view){
        MainActivity.redirectActivity(this, RoomActivity.class);
    }

    public void ClickLight(View view){
        MainActivity.redirectActivity(this, LightActivity.class);
    }

    public void handleLightClick(View view) {
        LinearLayout ground = findViewById(R.id.ground_floor);
        ground.setVisibility(View.GONE);
    }
    public void handleSocketClick(View view) {
        LinearLayout ground = findViewById(R.id.ground_floor);
        ground.setVisibility(View.GONE);
    }
    public void handleSunblindClick(View view) {
        LinearLayout ground = findViewById(R.id.ground_floor);
        ground.setVisibility(View.GONE);
    }
    public void handleSensorClick(View view) {
        LinearLayout ground = findViewById(R.id.ground_floor);
        ground.setVisibility(View.GONE);
    }
    public void handleGateClick(View view) {
        LinearLayout ground = findViewById(R.id.ground_floor);
        ground.setVisibility(View.GONE);
    }
}