package pwr.inteligentbuilding.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import pwr.inteligentbuilding.R;

public class MainActivity extends AppCompatActivity {
    Map<View, Intent> mainMenuItems = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        mainMenuItems.put((View) findViewById(R.id.roomsMenu), new Intent(MainActivity.this, RoomActivity.class));
        mainMenuItems.put((View) findViewById(R.id.floorsMenu), new Intent(MainActivity.this, FloorActivity.class));
        mainMenuItems.put((View) findViewById(R.id.lightsMenu), new Intent(MainActivity.this, LightActivity.class));
        mainMenuItems.put((View) findViewById(R.id.socketsMenu), new Intent(MainActivity.this, LightActivity.class));
        mainMenuItems.put((View) findViewById(R.id.temperatureMenu), new Intent(MainActivity.this, LightActivity.class));
        mainMenuItems.put((View) findViewById(R.id.settingsMenu), new Intent(MainActivity.this, SettingsActivity.class));

        mainMenuItems.keySet().forEach(view -> view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainMenuItems.get(view));
            }
        }));
    }
}