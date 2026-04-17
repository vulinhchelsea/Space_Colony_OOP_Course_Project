package com.example.spacecolony.activities;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacecolony.R;
import com.example.spacecolony.adapters.CrewAdapter;
import com.example.spacecolony.util.Storage;

public class TeamActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        RecyclerView rv = findViewById(R.id.rv_team_members);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new CrewAdapter(Storage.loadCrewMembers(this)));

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_help).setOnClickListener(v -> showHelp());
    }

    private void showHelp() {
        new AlertDialog.Builder(this)
            .setTitle("Team Help")
            .setMessage("This page shows all your recruited crew members. You can check their stats, level, and current energy here.")
            .setPositiveButton("OK", null)
            .show();
    }
}
