package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.utils.Drawer;

public class EditActionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Spinner deviceType;
    private Spinner deviceName;
    private ArrayAdapter<CharSequence> typeAdapter;
    private ArrayAdapter<CharSequence> nameAdapter;


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
        nameAdapter = ArrayAdapter.createFromResource(this, R.array.lights, android.R.layout.simple_spinner_item);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deviceType.setAdapter(typeAdapter);
        deviceType.setOnItemSelectedListener(this);
        deviceName.setAdapter(nameAdapter);
    }


    public void ClickMenu(View view) {
        Drawer.openDrawer(drawerLayout);
    }

    public void ClickFloor(View view) {
        Drawer.redirectActivity(this, FloorActivity.class);
    }

    public void ClickOPCUA(View view) {
        Drawer.redirectActivity(this, OpcuaActivity.class);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}