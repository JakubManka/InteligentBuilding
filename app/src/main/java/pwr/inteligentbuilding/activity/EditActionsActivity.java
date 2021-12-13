package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.utils.EditActionsActivityUtils;

public class EditActionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditActionsActivityUtils editActionsActivityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_actions);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        Spinner deviceType = findViewById(R.id.deviceType);
        Spinner deviceName = findViewById(R.id.deviceName);
        editActionsActivityUtils = new EditActionsActivityUtils(this, deviceType, deviceName);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        editActionsActivityUtils.onItemSelected(adapterView, i);
    }

    public void handleAddAction(View view) {
        editActionsActivityUtils.handleAddAction();
    }

    public void handleSaveAndQuit(View view) {
        editActionsActivityUtils.handleSaveAndQuit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}