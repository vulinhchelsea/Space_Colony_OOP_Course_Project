package com.example.spacecolony.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;
import com.example.spacecolony.core.CombatManager;
import com.example.spacecolony.crewmembers.CrewMember;
import com.example.spacecolony.threats.Threat;
import com.example.spacecolony.util.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombatPage extends AppCompatActivity {
    private TextView tvRound, tvLog;
    private CombatManager manager;
    private int targetIdx = 0;
    private boolean waiting = false;
    
    // view caching
    private final ImageView[] eImgs = new ImageView[2];
    private final TextView[] eTxts = new TextView[2];
    private final TextView[] eNames = new TextView[2];
    private final View[] cLayouts = new View[3];
    private final ImageView[] cImgs = new ImageView[3];
    private final TextView[] cTxts = new TextView[3];
    private final Button[] cBtns = new Button[3];
    private final TextView[] cNames = new TextView[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat_page);

        // initialize list
        List<CrewMember> squad = (List<CrewMember>) getIntent().getSerializableExtra("squad");
        List<Threat> threats = (List<Threat>) getIntent().getSerializableExtra("threats");

        if (squad != null && threats != null) {
            manager = new CombatManager(squad, threats);
            init();
            updateUI();
        } else finish();
    }

    // initialize UI
    private void init() {
        tvRound = findViewById(R.id.tv_round);
        tvLog = findViewById(R.id.tv_combat_log);
        
        // cache enemy views
        int[] eImgIds = {R.id.iv_enemy_1_img, R.id.iv_enemy_2_img};
        int[] eTxtIds = {R.id.tv_enemy_1_energy, R.id.tv_enemy_2_energy};
        int[] eNameIds = {R.id.tv_enemy_1_name, R.id.tv_enemy_2_name};
        for (int i = 0; i < 2; i++) {
            eImgs[i] = findViewById(eImgIds[i]);
            eTxts[i] = findViewById(eTxtIds[i]);
            eNames[i] = findViewById(eNameIds[i]);
        }

        // cache crew views
        int[] cLIds = {R.id.layout_crew_1, R.id.layout_crew_2, R.id.layout_crew_3};
        int[] cIIds = {R.id.iv_crew_1_img, R.id.iv_crew_2_img, R.id.iv_crew_3_img};
        int[] cTIds = {R.id.tv_crew_1_energy, R.id.tv_crew_2_energy, R.id.tv_crew_3_energy};
        int[] cBIds = {R.id.btn_crew_1_attack, R.id.btn_crew_2_attack, R.id.btn_crew_3_attack};
        int[] cNIds = {R.id.tv_crew_1_name, R.id.tv_crew_2_name, R.id.tv_crew_3_name};
        for (int i = 0; i < 3; i++) {
            cLayouts[i] = findViewById(cLIds[i]);
            cImgs[i] = findViewById(cIIds[i]);
            cTxts[i] = findViewById(cTIds[i]);
            cBtns[i] = findViewById(cBIds[i]);
            cNames[i] = findViewById(cNIds[i]);
        }

        findViewById(R.id.enemy_1_slot).setOnClickListener(v -> select(0));
        findViewById(R.id.enemy_2_slot).setOnClickListener(v -> select(1));

        for (int i = 0; i < 3; i++) {
            int idx = i;
            cBtns[i].setOnClickListener(v -> attack(idx));
        }
        
        findViewById(R.id.btn_help).setOnClickListener(v -> showHelp());
        select(0);
    }

    // select enemy
    private void select(int i) {
        if (i >= manager.getThreats().size()) return;
        targetIdx = i;
        findViewById(R.id.iv_enemy_1_selector).setVisibility(i == 0 ? View.VISIBLE : View.INVISIBLE);
        findViewById(R.id.iv_enemy_2_selector).setVisibility(i == 1 ? View.VISIBLE : View.INVISIBLE);
    }

    // waiting logic to only deal dmg once per turn
    private void attack(int crewIdx) {
        if (waiting || crewIdx >= manager.getSquad().size()) return;
        CrewMember c = manager.getSquad().get(crewIdx);
        Threat t = manager.getThreats().get(targetIdx);
        
        if (c.isDefeated() || t.isDefeated()) return;

        waiting = true;
        log(manager.executeAttack(c, t));
        updateUI();

        if (manager.isVictory()) end(true);
        else new Handler().postDelayed(this::enemyTurn, 1000);
    }

    // random attacking from enemy logic
    private void enemyTurn() {
        if (manager.isVictory() || manager.isDefeat()) return;
        
        Random r = new Random();
        List<Threat> aliveT = new ArrayList<>();
        for (Threat x : manager.getThreats()) if (!x.isDefeated()) aliveT.add(x);
        
        List<CrewMember> aliveC = new ArrayList<>();
        for (CrewMember x : manager.getSquad()) if (!x.isDefeated()) aliveC.add(x);

        if (!aliveT.isEmpty() && !aliveC.isEmpty()) {
            Threat t = aliveT.get(r.nextInt(aliveT.size()));
            CrewMember target = aliveC.get(r.nextInt(aliveC.size()));
            log(manager.executeAttack(t, target));
        }
        updateUI();

        if (manager.isDefeat()) end(false);
        else {
            manager.nextRound();
            tvRound.setText("Round " + manager.getRound());
            waiting = false;
        }
    }

    // important: update UI after every action
    private void updateUI() {
        for (int i = 0; i < 2; i++) {
            if (i < manager.getThreats().size()) {
                Threat t = manager.getThreats().get(i);
                eNames[i].setText(t.getName());
                updateEntity(t, eImgs[i], eTxts[i]);
            }
        }

        for (int i = 0; i < 3; i++) {
            if (i < manager.getSquad().size()) {
                cLayouts[i].setVisibility(View.VISIBLE);
                CrewMember m = manager.getSquad().get(i);
                cNames[i].setText(m.getName());
                updateEntity(m, cImgs[i], cTxts[i]);
                cBtns[i].setEnabled(!m.isDefeated());
            } else cLayouts[i].setVisibility(View.GONE);
        }
    }

    // update method for both threats and crew (energy and defeated)
    private void updateEntity(com.example.spacecolony.core.Entity e, ImageView iv, TextView tv) {
        iv.setImageResource(e instanceof Threat ? ((Threat) e).getIconResource() : ((CrewMember) e).getAvatarResource());
        tv.setText(e.isDefeated() ? "DEFEATED" : "ENERGY: " + e.getEnergy() + "/" + e.getMaxEnergy());
        iv.setAlpha(e.isDefeated() ? 0.3f : 1.0f);
    }

    private void log(String s) { tvLog.setText(s + "\n" + tvLog.getText()); }

    private void end(boolean vic) {
        if (vic) {
            manager.rewardCrew();
            Storage.finishMission(this);
            if (Storage.getCompletedMissions(this) >= 10) {
                showWinDialog();
                return;
            }
        }
        
        Toast.makeText(this, vic ? "VICTORY!" : "DEFEAT...", Toast.LENGTH_LONG).show();
        saveAndExit();
    }

    private void showWinDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Congratulations!")
            .setMessage("You have successfully completed 10 missions and secured the colony! Thank you for playing Space Colony.")
            .setCancelable(false)
            .setPositiveButton("Reset & Restart", (dialog, which) -> {
                Storage.clearData(this);
                startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            })
            .show();
    }

    private void showHelp() {
        new AlertDialog.Builder(this)
            .setTitle("Combat Help")
            .setMessage("Eliminate all threats to win!\n\n- Tap an enemy to select target (arrow icon shows selection).\n- Tap ATK on a crew member to attack the selected enemy.\n- Damage = Skill - Resilience (x1.5 if attacking weakness).\n- Enemies attack your crew randomly.")
            .setPositiveButton("OK", null)
            .show();
    }

    // save status of crew and exit
    private void saveAndExit() {
        List<CrewMember> all = Storage.loadCrewMembers(this);
        for (CrewMember s : manager.getSquad()) {
            if (!s.isDefeated()) s.setEnergy(s.getMaxEnergy());
            for (int i = 0; i < all.size(); i++) if (all.get(i).getName().equals(s.getName())) all.set(i, s);
        }
        Storage.saveCrewMembers(this, all);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }, 2000);
    }
}
