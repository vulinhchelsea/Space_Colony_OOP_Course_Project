package com.example.spacecolony.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacecolony.R;
import com.example.spacecolony.adapters.MedBayAdapter;
import com.example.spacecolony.core.Simulator;
import com.example.spacecolony.crewmembers.CrewMember;
import com.example.spacecolony.util.Storage;
import java.util.ArrayList;
import java.util.List;

public class MedBayActivity extends AppCompatActivity {
    private RecyclerView rv;
    private final Simulator sim = new Simulator();
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_med_bay);
        
        tvStatus = findViewById(R.id.tv_medbay_status);
        rv = findViewById(R.id.rv_medbay_members);
        rv.setLayoutManager(new LinearLayoutManager(this));

        View left = findViewById(R.id.left_container);
        if (left != null) {
            ViewCompat.setOnApplyWindowInsetsListener(left, (v, insets) -> {
                Insets s = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(s.left, s.top, 0, s.bottom);
                return insets;
            });
        }

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_help).setOnClickListener(v -> showHelp());
        refresh();
    }

    // cooldown logic depends on mission count
    private void refresh() {
        boolean ok = sim.isMedbayAvailable(this);
        tvStatus.setText(ok ? "medbay ready" : "cooling down...");
        // list of injured members
        List<CrewMember> injured = new ArrayList<>();
        for (CrewMember m : Storage.loadCrewMembers(this)) {
            if (m.getEnergy() < m.getMaxEnergy()) injured.add(m);
        }

        rv.setAdapter(new MedBayAdapter(this, injured, ok, () -> {
            if (ok) {
                sim.useMedbay(this);
                refresh();
            }
        }));
    }

    private void showHelp() {
        new AlertDialog.Builder(this)
            .setTitle("MedBay Help")
            .setMessage("Heal your defeated or injured crew members here. Using the MedBay has a cooldown period. Complete missions to make it available again.")
            .setPositiveButton("OK", null)
            .show();
    }
}
