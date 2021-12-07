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

public class EditActionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Spinner deviceType;
    private Spinner deviceName;
    private ArrayAdapter<CharSequence> typeAdapter;
    private ArrayAdapter<CharSequence> nameAdapter;
    private List<Action> allActions;
    private List<Action> adapterActions;
    private DemoAdapter adapter;
    private Map<String, ArrayAdapter<CharSequence>> adapterMap;
    private List<Device> devices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_actions);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        drawerLayout = findViewById(R.id.drawerLayout);
        deviceType = findViewById(R.id.deviceType);
        deviceName = findViewById(R.id.deviceName);

        typeAdapter = ArrayAdapter.createFromResource(this, R.array.deviceType, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        deviceType.setAdapter(typeAdapter);
        deviceType.setOnItemSelectedListener(this);
        deviceName.setOnItemSelectedListener(this);

        EditActionsActivityUtils utils = new EditActionsActivityUtils(this);
        devices = utils.getDevices();

//        devices = (Devices) getIntent().getExtras().get("Devices");
//        System.out.println(devices.getDevices().size());

        adapterMap = new HashMap<>();
        List<String> list = Arrays.asList(getResources().getStringArray(R.array.deviceType));
        adapterMap.put(list.get(0), ArrayAdapter.createFromResource(this, R.array.lights, android.R.layout.simple_spinner_item));
        adapterMap.put(list.get(1), ArrayAdapter.createFromResource(this, R.array.sensors, android.R.layout.simple_spinner_item));
        adapterMap.put(list.get(2), ArrayAdapter.createFromResource(this, R.array.sockets, android.R.layout.simple_spinner_item));
        adapterMap.put(list.get(3), ArrayAdapter.createFromResource(this, R.array.sunblinds, android.R.layout.simple_spinner_item));
        adapterMap.put(list.get(4), ArrayAdapter.createFromResource(this, R.array.gate, android.R.layout.simple_spinner_item));

        RecyclerView recyclerView = findViewById(R.id.actionsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void handleAddAction(View view) {
        Optional<Action> action = allActions.stream().filter(a -> a.getTriggerFunction().equals("")).findFirst();
        if (adapterActions.size() < 10) {
            Action newAction = allActions.get(adapterActions.size());
            adapterActions.add(newAction);
            adapter.notifyItemInserted(allActions.size() - 1);
        } else {
            Toast.makeText(this, "Nie można mieć więcej niż 10 akcji", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        if (adapterMap.containsKey(text)) {
//            devices.stream().filter(d -> d.getType().equals(text));
            nameAdapter = adapterMap.get(text);
            nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            deviceName.setAdapter(nameAdapter);
            Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
        }
        Optional<Device> device = devices.stream().filter(d -> d.getRoom().equals(text)).findFirst();
        if (device.isPresent()) {
            allActions = device.get().getActions();
            adapterActions = device.get().getActions().stream().filter(a -> !a.getTriggerFunction().equals("")).collect(Collectors.toList());
            adapter = new DemoAdapter(adapterActions);
            RecyclerView recyclerView = findViewById(R.id.actionsList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void ClickMenu(View view) {
        Drawer.openDrawer(drawerLayout);
    }

    public void ClickFloor(View view) {
        Drawer.redirectActivity(this, MainActivity.class);
    }

    public void ClickActions(View view) {
        Drawer.redirectActivity(this, EditActionsActivity.class);
    }

    public void handleSaveAndQuit(View view) {
        System.out.println(adapterActions.size());
    }
}