package pwr.inteligentbuilding.utils;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import pwr.inteligentbuilding.adapter.ActionsAdapter;
import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.activity.EditActionsActivity;
import pwr.inteligentbuilding.model.Action;
import pwr.inteligentbuilding.model.Device;
import pwr.inteligentbuilding.utils.opcUtils.ManagerOPC;
import pwr.inteligentbuilding.utils.opcUtils.SessionElement;

public class EditActionsActivityUtils {
    private final List<Device> devices;
    private final EditActionsActivity activity;
    private final Spinner deviceType;
    private final Spinner deviceName;
    private List<Action> allActions;
    private List<Action> adapterActions;
    private ActionsAdapter adapter;
    private Map<String, ArrayAdapter<CharSequence>> adapterMap;

    public EditActionsActivityUtils(EditActionsActivity activity, Spinner deviceType, Spinner deviceName) {
        this.activity = activity;
        devices = DevicesUtils.getDevices(this.activity);
        this.deviceType = deviceType;
        this.deviceName = deviceName;
        updateStatus();
    }

    public void updateStatus() {
        Optional<Device> device = devices.stream().filter(d ->
                d.getRoom().equals((String) activity.getIntent().getExtras().get("room"))).findFirst();
        Thread readOneAction = new Thread() {
            @Override
            public void run() {
                if(device.isPresent()){
                    device.get().updateActions();
                    activity.runOnUiThread(() -> updateView());
                }
            }
        };
        readOneAction.start();

        Thread readActions = new Thread() {
            @Override
            public void run() {
                devices.forEach(Device::updateActions);
            }
        };
        readActions.start();
    }

    public void handleAddAction() {
        if (adapterActions.size() < 10) {
            Action newAction = allActions.get(adapterActions.size());
            adapterActions.add(newAction);
            adapter.notifyItemInserted(allActions.size() - 1);
        } else {
            Toast.makeText(activity, "Nie można mieć więcej niż 10 akcji", Toast.LENGTH_SHORT).show();
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, int i) {
        String text = adapterView.getItemAtPosition(i).toString();
        if (adapterMap.containsKey(text)) {
            ArrayAdapter<CharSequence> nameAdapter = adapterMap.get(text);
            nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            deviceName.setAdapter(nameAdapter);
            deviceName.setSelection(nameAdapter.getPosition((String) activity.getIntent().getExtras().get("room")));
        }
        Optional<Device> device = devices.stream().filter(d -> d.getRoom().equals(text)).findFirst();
        if (device.isPresent()) {
            allActions = device.get().getActions();
            adapterActions = device.get().getActions().stream().filter(a -> !a.getTriggerFunction().equals("")).collect(Collectors.toList());
            adapter = new ActionsAdapter(adapterActions);
            RecyclerView recyclerView = activity.findViewById(R.id.actionsList);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setAdapter(adapter);
        }
    }

    public void handleSaveAndQuit() {
        ManagerOPC manager = ManagerOPC.getIstance();
        SessionElement sessionElement = manager.getSessions().get(0);
        if (adapterActions.size() < 10) {
            for (int i = adapterActions.size(); i < 10; i++) {
                adapterActions.add(allActions.get(i));
            }
        }
        adapterActions.forEach(a -> a.writeActions(sessionElement));
        activity.finish();
    }

    private void updateView() {
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(activity, R.array.deviceType, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        deviceType.setAdapter(typeAdapter);
        deviceType.setOnItemSelectedListener(activity);
        deviceType.setSelection(typeAdapter.getPosition((String) activity.getIntent().getExtras().get("type")));
        deviceName.setOnItemSelectedListener(activity);

        setAdapterMap();

        RecyclerView recyclerView = activity.findViewById(R.id.actionsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }

    private void setAdapterMap() {
        adapterMap = new HashMap<>();
        List<String> list = Arrays.asList(activity.getResources().getStringArray(R.array.deviceType));
        adapterMap.put(list.get(0), ArrayAdapter.createFromResource(activity, R.array.lights, android.R.layout.simple_spinner_item));
        adapterMap.put(list.get(1), ArrayAdapter.createFromResource(activity, R.array.sensors, android.R.layout.simple_spinner_item));
        adapterMap.put(list.get(2), ArrayAdapter.createFromResource(activity, R.array.sockets, android.R.layout.simple_spinner_item));
        adapterMap.put(list.get(3), ArrayAdapter.createFromResource(activity, R.array.sunblinds, android.R.layout.simple_spinner_item));
        adapterMap.put(list.get(4), ArrayAdapter.createFromResource(activity, R.array.gate, android.R.layout.simple_spinner_item));
    }
}