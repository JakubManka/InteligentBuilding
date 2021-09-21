package pwr.inteligentbuilding.activity;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import pwr.inteligentbuilding.R;

public class RoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        ImageButton undo = findViewById(R.id.undo);
        undo.setOnClickListener(v -> startActivity(new Intent(RoomActivity.this, MainActivity.class)));
        LinearLayout bedRoomLayout = findViewById(R.id.bed_room_layout);

        Button bedroomCollapse = findViewById(R.id.bed_room_button_collapse);
        bedroomCollapse.setOnClickListener(v -> {
            if(bedRoomLayout.getVisibility() == View.VISIBLE ){
                bedRoomLayout.setVisibility(View.GONE);
            }else{
                bedRoomLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}