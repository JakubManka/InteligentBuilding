package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import pwr.inteligentbuilding.R;
import pwr.inteligentbuilding.utils.DevicesUtils;
import pwr.inteligentbuilding.utils.OpcuaConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        DevicesUtils devices = new DevicesUtils();

        OpcuaConnection opcua = new OpcuaConnection(this, getFilesDir(), devices);
        opcua.connect();

        Intent intent = new Intent(this, FloorActivity.class);
        intent.putExtra("devices", devices);
        this.startActivity(intent);
    }
}