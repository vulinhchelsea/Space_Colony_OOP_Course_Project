package com.example.spacecolony.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacecolony.R;
import com.example.spacecolony.adapters.CrewAdapter;
import com.example.spacecolony.core.Simulator;
import com.example.spacecolony.crewmembers.CrewMember;
import com.example.spacecolony.util.Storage;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.List;

public class TrainingActivity extends AppCompatActivity {
    private final Simulator sim = new Simulator();
    private List<CrewMember> list;
    private CrewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        list = Storage.loadCrewMembers(this);
        adapter = new CrewAdapter(list);
        adapter.setOnItemClickListener((m, p) -> showDetails(m, p));
        
        RecyclerView rv = findViewById(R.id.rv_training_members);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_help).setOnClickListener(v -> showHelp());
    }

    private void showHelp() {
        new AlertDialog.Builder(this)
            .setTitle("Training Help")
            .setMessage("Improve your crew's skills here! Training grants XP to your members. Note that the simulator has a cooldown after use.")
            .setPositiveButton("OK", null)
            .show();
    }

    private void showDetails(CrewMember m, int p) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View v = getLayoutInflater().inflate(R.layout.layout_training_bottom_sheet, null);
        
        ((TextView) v.findViewById(R.id.bs_name)).setText(m.getName());
        ((TextView) v.findViewById(R.id.bs_role)).setText("role: " + m.getType());
        ((TextView) v.findViewById(R.id.bs_stats)).setText("lvl: " + m.getLevel() + " exp: " + m.getExp());

        v.findViewById(R.id.btn_bs_train).setOnClickListener(view -> {
            if (sim.isTrainingAvailable(this)) {
                m.gainExp(25); // updated to 25XP
                sim.useTraining(this);
                Storage.saveCrewMembers(this, list);
                adapter.notifyItemChanged(p);
                dialog.dismiss();
            } else Toast.makeText(this, "cooling down...", Toast.LENGTH_SHORT).show();
        });

        dialog.setContentView(v);
        dialog.show();
    }
}
