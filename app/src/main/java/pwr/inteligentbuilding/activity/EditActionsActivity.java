package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import pwr.inteligentbuilding.DemoAdapter;
import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.model.Action;
import pwr.inteligentbuilding.model.Device;
import pwr.inteligentbuilding.utils.Drawer;
import pwr.inteligentbuilding.utils.EditActionsActivityUtils;
import pwr.inteligentbuilding.utils.opcUtils.ManagerOPC;
import pwr.inteligentbuilding.utils.opcUtils.SessionElement;

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