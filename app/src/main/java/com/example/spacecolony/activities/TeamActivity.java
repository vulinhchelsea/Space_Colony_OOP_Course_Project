package com.example.spacecolony.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacecolony.crewmembers.CrewMember;
import com.example.spacecolony.R;
import com.example.spacecolony.adapters.CrewAdapter;
import com.example.spacecolony.crewmembers.Medic;
import com.example.spacecolony.crewmembers.Soldier;
import com.example.spacecolony.util.Storage;

import java.util.List;

public class TeamActivity extends AppCompatActivity {

    private RecyclerView rvTeamMembers;
    private CrewAdapter adapter;
    private List<CrewMember> crewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        // view from layout
        rvTeamMembers = findViewById(R.id.rv_team_members);

        // load data from data storage
        crewList = Storage.loadCrewMembers(this);

        // if there is no data, create initial data
        if (crewList.isEmpty()) {
            createInitialTeam();
        }

        // set up recycler view
        setupRecyclerView();

        // back button
        View btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            // return to main activity
            finish();
        });
    }

    private void setupRecyclerView() {
        // LinearLayoutManager
        rvTeamMembers.setLayoutManager(new LinearLayoutManager(this));

        // create adapter and set to RecyclerView
        adapter = new CrewAdapter(crewList);
        rvTeamMembers.setAdapter(adapter);
    }

    private void createInitialTeam() {
        Storage.addCrewMember(this, new Medic("Hai Anh"));
        Storage.addCrewMember(this, new Soldier("K. Kang"));
        // update data storage
        crewList = Storage.loadCrewMembers(this);
    }
}
