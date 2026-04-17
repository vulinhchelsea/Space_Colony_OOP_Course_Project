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
import com.example.spacecolony.adapters.RecruitAdapter;
import com.example.spacecolony.core.Simulator;
import com.example.spacecolony.crewmembers.*;
import java.util.ArrayList;
import java.util.List;

public class RecruitmentActivity extends AppCompatActivity {
    private final Simulator sim = new Simulator();
    private final List<CrewMember> list = new ArrayList<>();
    private RecruitAdapter adapter;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recruitment);
        
        tv = findViewById(R.id.tv_recruit_status);
        View left = findViewById(R.id.left_container);
        if (left != null) {
            ViewCompat.setOnApplyWindowInsetsListener(left, (v, insets) -> {
                Insets s = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(s.left, s.top, 0, s.bottom);
                return insets;
            });
        }

        RecyclerView rv = findViewById(R.id.recycler_recruitment);
        rv.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new RecruitAdapter(this, list, () -> {
            if (sim.isRecruitAvailable(this)) {
                sim.useRecruit(this);
                refresh();
            }
        });
        rv.setAdapter(adapter);
        
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_help).setOnClickListener(v -> showHelp());
        refresh();
    }

    private void refresh() {
        boolean ok = sim.isRecruitAvailable(this);
        tv.setText(ok ? "open" : "closed...");
        list.clear();
        if (ok) {
            list.add(new Medic(""));
            list.add(new Soldier(""));
            list.add(new Pilot(""));
            list.add(new Engineer(""));
            list.add(new Scientist(""));
        }
        adapter.notifyDataSetChanged();
    }

    private void showHelp() {
        new AlertDialog.Builder(this)
            .setTitle("Recruitment Help")
            .setMessage("Hire new members for your colony. Each class has different base stats. Recruitment has a cooldown after each hire.")
            .setPositiveButton("OK", null)
            .show();
    }
}
