package pwr.inteligentbuilding.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pwr.inteligentbuilding.R;

public class EditActionsActivity extends AppCompatActivity {

    private ExpandableListView devicesView;
    private List<String> deviceTypes;
    private Map<String, List<String>> devices;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_actions);

        devicesView = findViewById(R.id.devices);
        devices = new HashMap<>();
        deviceTypes = new ArrayList<>();
        drawerLayout = findViewById(R.id.drawerLayout);
    }

    private void initListData(){
        deviceTypes.add(getString(R.string.lights));
        deviceTypes.add(getString(R.string.sockets));
        deviceTypes.add(getString(R.string.sunblinds));
        deviceTypes.add(getString(R.string.sensors));
        deviceTypes.add(getString(R.string.gate));

        List<String> lights = Arrays.asList(getResources().getStringArray(R.array.lights));
        List<String> sensors = Arrays.asList(getResources().getStringArray(R.array.sensors));
        List<String> sockets = Arrays.asList(getResources().getStringArray(R.array.sockets));
        List<String> sunblinds = Arrays.asList(getResources().getStringArray(R.array.sunblinds));
        List<String> gate = Arrays.asList(getResources().getStringArray(R.array.gate));

        devices.put(deviceTypes.get(0), lights);
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

    public void ClickOPCUA(View view) {
        MainActivity.redirectActivity(this, OpcuaActivity.class);
    }
}