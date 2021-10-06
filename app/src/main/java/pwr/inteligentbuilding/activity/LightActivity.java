package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.HashMap;
import java.util.Map;

import pwr.inteligentbuilding.R;

public class LightActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    Map<SwitchMaterial, Boolean> lights = new HashMap<>();
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        drawerLayout = findViewById(R.id.drawerLayout);

        lights.put((SwitchMaterial) findViewById(R.id.lightning), false);
        lights.put((SwitchMaterial) findViewById(R.id.kitchen), false);
        lights.put((SwitchMaterial) findViewById(R.id.bed_room), false);
        lights.put((SwitchMaterial) findViewById(R.id.living_room), false);

        lights.forEach((light, checked) -> light.setOnCheckedChangeListener(this));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        lights.put((SwitchMaterial) findViewById(buttonView.getId()), isChecked);

        updateLightsView(buttonView, isChecked);

    }

    private void updateLightsView(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == R.id.lightning && !isAnyLightChecked()) {
            lights.replaceAll((lights, clicked) -> clicked = isChecked);

        } else if (buttonView.getId() == R.id.lightning && !isChecked) {
            lights.replaceAll((lights, clicked) -> clicked = isChecked);

        } else if (isAnyLightChecked()) {
            lights.put((SwitchMaterial) findViewById(R.id.lightning), true);
        } else {
            lights.replaceAll((lights, clicked) -> clicked = false);
        }

        lights.forEach((light, clicked) -> {
            light.setChecked(clicked);
        });
    }

    private boolean isAnyLightChecked() {
        boolean[] isClicked = {false};
        lights.forEach((lights, clicked) -> {
            if (lights.getId() != R.id.lightning && clicked) {
                isClicked[0] = true;
            }
        });
        return isClicked[0];
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

    public void ClickOPCUA(View view){
        MainActivity.redirectActivity(this, OpcuaActivity.class);
    }
}