package com.example.spacecolony.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacecolony.R;
import com.example.spacecolony.adapters.CrewAdapter;
import com.example.spacecolony.core.MissionControl;
import com.example.spacecolony.crewmembers.CrewMember;
import com.example.spacecolony.threats.Threat;
import com.example.spacecolony.util.Storage;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MissionActivity extends AppCompatActivity {
    private final ImageView[] slotIvs = new ImageView[3];
    private final CrewMember[] selectedSlots = new CrewMember[3];
    private final MissionControl missionControl = new MissionControl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mission);

        // setup insets
        View left = findViewById(R.id.left_container);
        if (left != null) {
            ViewCompat.setOnApplyWindowInsetsListener(left, (v, insets) -> {
                Insets system = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(system.left, system.top, 0, system.bottom);
                return insets;
            });
        }

        // init slots
        int[] ids = {R.id.ivSelectMember1, R.id.ivSelectMember2, R.id.ivSelectMember3};
        for (int i = 0; i < 3; i++) {
            int idx = i;
            slotIvs[i] = findViewById(ids[i]);
            slotIvs[idx].setOnClickListener(v -> showPicker(idx));
        }

        // navigation
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_help).setOnClickListener(v -> showHelp());
        findViewById(R.id.btnStart).setOnClickListener(v -> startMission());

        // setup threats
        missionControl.generateMissionThreats(this, Storage.getCompletedMissions(this), 2);
        updateThreatUI();
    }

    private void showHelp() {
        new AlertDialog.Builder(this)
            .setTitle("Mission Help")
            .setMessage("Select up to 3 crew members for the mission. Check the THREATS section to see your enemies. We recommend choosing crew members that can exploit the enemies' WEAKNESS for bonus damage!")
            .setPositiveButton("Got it", null)
            .show();
    }

    private void startMission() {
        missionControl.setSquad(selectedSlots);
        if (missionControl.getCurrentSquad().isEmpty()) {
            Toast.makeText(this, "select at least one member", Toast.LENGTH_SHORT).show();
            return;
        }
        // go to combat
        Intent intent = new Intent(this, CombatPage.class);
        intent.putExtra("squad", (Serializable) missionControl.getCurrentSquad());
        intent.putExtra("threats", (Serializable) missionControl.getCurrentThreats());
        startActivity(intent);
        finish();
    }

    // update img
    private void updateThreatUI() {
        int[] layouts = {R.id.layoutEnemy1, R.id.layoutEnemy2};
        int[] imgs = {R.id.ivEnemy1, R.id.ivEnemy2};
        int[] names = {R.id.tvEnemy1Name, R.id.tvEnemy2Name};
        int[] status = {R.id.tvEnemy1Status, R.id.tvEnemy2Status};
        List<Threat> threats = missionControl.getCurrentThreats();

        for (int i = 0; i < 2; i++) {
            View layout = findViewById(layouts[i]);
            if (i < threats.size()) {
                layout.setVisibility(View.VISIBLE);
                Threat t = threats.get(i);
                ((TextView) findViewById(names[i])).setText(t.getName());
                ((ImageView) findViewById(imgs[i])).setImageResource(t.getIconResource());
                ((TextView) findViewById(status[i])).setText("ENERGY: " + t.getEnergy());
            } else layout.setVisibility(View.GONE);
        }
    }

    // bottom sheet for picking crew
    private void showPicker(int slot) {
        // load crew list
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.layout_crew_selection_bottom_sheet, null);
        dialog.setContentView(view);

        List<CrewMember> all = Storage.loadCrewMembers(this);
        List<CrewMember> available = new ArrayList<>();
        for (CrewMember m : all) {
            boolean picked = false;
            for (CrewMember s : selectedSlots) if (s != null && s.getName().equals(m.getName())) picked = true;
            // only take available crew (not 0 energy)
            if (m.getEnergy() > 0 && !picked) available.add(m);
        }

        CrewAdapter adapter = new CrewAdapter(available);
        adapter.setOnItemClickListener((m, p) -> {
            selectedSlots[slot] = m;
            slotIvs[slot].setImageResource(m.getAvatarResource());
            slotIvs[slot].setPadding(0, 0, 0, 0);
            dialog.dismiss();
        });
        ((RecyclerView) view.findViewById(R.id.rvCrewSelection)).setAdapter(adapter);
        dialog.show();
    }
}
