package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import pwr.inteligentbuilding.R;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        drawerLayout = findViewById(R.id.drawerLayout);
    }
    
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickFloor(View view){
        redirectActivity(this, FloorActivity.class);
    }

    public void ClickRoom(View view){
        redirectActivity(this, RoomActivity.class);
    }

    public void ClickLight(View view){
        redirectActivity(this, LightActivity.class);
    }

    public void ClickOPCUA(View view){
        redirectActivity(this, OpcuaActivity.class);
    }

    public static void redirectActivity(Activity activity, Class aClass){
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}