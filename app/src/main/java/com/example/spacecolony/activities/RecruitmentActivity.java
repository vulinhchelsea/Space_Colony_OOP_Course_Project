package com.example.spacecolony.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacecolony.R;
import com.example.spacecolony.adapters.RecruitAdapter;
import com.example.spacecolony.core.Quarter;
import com.example.spacecolony.crewmembers.CrewMember;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RecruitmentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecruitAdapter adapter;
    private List<CrewMember> candidates;
    private Quarter quarter;

    // UI elements
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recruitment);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        quarter = new Quarter();
        setupRecyclerView();
        
        FloatingActionButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        generateCandidates();
    }

    // recycler view
    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_recruitment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        candidates = new ArrayList<>();
        adapter = new RecruitAdapter(this, candidates);
        recyclerView.setAdapter(adapter);
    }


    private void generateCandidates() {
        // generate 5 random candidates for the player to choose from
        for (int i = 0; i < 5; i++) {
            candidates.add(quarter.generateRandomCandidate());
        }
        adapter.notifyDataSetChanged();
    }
}
