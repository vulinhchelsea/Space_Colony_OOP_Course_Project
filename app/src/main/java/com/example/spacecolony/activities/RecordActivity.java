package com.example.spacecolony.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.spacecolony.R;
import com.example.spacecolony.util.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_record);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupUI();
    }

    private void setupUI() {
        TextView txtRecruitCount = findViewById(R.id.txt_recruit_count);
        TextView txtKillCount = findViewById(R.id.txt_kill_count);
        Button btnReset = findViewById(R.id.btn_reset_game);

        // display current stats
        int count = Storage.countCrewMembers(this);
        txtRecruitCount.setText("Recruit " + count + " crew members");
        txtKillCount.setText("kill 0" + " enemies"); // Placeholder for now

        btnReset.setOnClickListener(v -> showResetConfirmation());
        
        // setup back button if visible
        FloatingActionButton btnBack = findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    // message for reset confirmation
    private void showResetConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Abandon Colony?")
                .setMessage("Are you sure you want to restart? All crew members and mission progress will be lost forever.")
                .setPositiveButton("RESTART", (dialog, which) -> {
                    Storage.clearAllData(this);
                    
                    // restart the app by going to MainActivity and clearing task stack
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("CANCEL", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
