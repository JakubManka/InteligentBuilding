package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import pwr.inteligentbuilding.R;

public class RoomActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        LinearLayout bedRoomLayout = findViewById(R.id.bed_room_layout);
        drawerLayout = findViewById(R.id.drawerLayout);

        Button bedroomCollapse = findViewById(R.id.bed_room_button_collapse);
        bedroomCollapse.setOnClickListener(v -> {
            if (bedRoomLayout.getVisibility() == View.VISIBLE) {
                bedRoomLayout.setVisibility(View.GONE);
            } else {
                bedRoomLayout.setVisibility(View.VISIBLE);
            }
        });
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
}