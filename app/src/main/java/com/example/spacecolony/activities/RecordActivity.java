package com.example.spacecolony.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;
import com.example.spacecolony.util.Storage;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class RecordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        // load status
        ((TextView) findViewById(R.id.txt_recruit_count)).setText("recruited " + Storage.getTotalRecruited(this) + " members");
        ((TextView) findViewById(R.id.txt_kill_count)).setText("killed 0 enemies");
        
        TextView missionTxt = findViewById(R.id.txt_mission_count);
        if (missionTxt != null) missionTxt.setText("accomplished " + Storage.getCompletedMissions(this) + " missions");

        findViewById(R.id.btn_reset_game).setOnClickListener(v -> showReset());
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void showReset() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View v = getLayoutInflater().inflate(R.layout.layout_reset_bottom_sheet, null);
        
        v.findViewById(R.id.btn_confirm_reset).setOnClickListener(view -> {
            Storage.clearData(this);
            dialog.dismiss();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
        
        v.findViewById(R.id.btn_cancel_reset).setOnClickListener(view -> dialog.dismiss());
        dialog.setContentView(v);
        dialog.show();
    }
}
