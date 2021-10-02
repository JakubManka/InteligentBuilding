package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

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
}