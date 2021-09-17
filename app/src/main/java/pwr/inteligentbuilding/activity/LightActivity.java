package pwr.inteligentbuilding.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.HashMap;
import java.util.Map;

import pwr.inteligentbuilding.R;

public class LightActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener  {
    Map<SwitchMaterial, Boolean> lights = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

//        lights.put(findViewById(R.id.oswietlenie, false);
        lights.put((SwitchMaterial) findViewById(R.id.oswietlenie), false);
        lights.put((SwitchMaterial) findViewById(R.id.kuchnia), false);
        lights.put((SwitchMaterial) findViewById(R.id.sypialnia), false);
        lights.put((SwitchMaterial) findViewById(R.id.salon), false);


        lights.forEach((light, checked) -> light.setOnCheckedChangeListener(this));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        lights.put((SwitchMaterial) findViewById(buttonView.getId()), isChecked);

        updateLightsView(buttonView, isChecked);

    }

    private void updateLightsView(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == R.id.oswietlenie && !isAnyLightChecked()) {
            lights.replaceAll((lights, clicked) -> clicked = isChecked);

        } else if (buttonView.getId() == R.id.oswietlenie && !isChecked) {
            lights.replaceAll((lights, clicked) -> clicked = isChecked);

        } else if (isAnyLightChecked()) {
            lights.put((SwitchMaterial) findViewById(R.id.oswietlenie), true);
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
            if (lights.getId() != R.id.oswietlenie && clicked) {
                isClicked[0] = true;
            }
        });
        return isClicked[0];
    }
}