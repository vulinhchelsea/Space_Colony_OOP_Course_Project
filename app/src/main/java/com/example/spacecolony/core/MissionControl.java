package com.example.spacecolony.core;

import com.example.spacecolony.threats.*;
import com.example.spacecolony.crewmembers.CrewMember;
import com.example.spacecolony.util.Storage;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MissionControl {
    private List<Threat> currentThreats = new ArrayList<>();
    private List<CrewMember> currentSquad = new ArrayList<>();
    private final Random rand = new Random();

    // random threat generator
    public Threat createMission(Context context) {
        int r = rand.nextInt(5);
        Threat t;
        if (r == 0) t = new GreyAlien();
        else if (r == 1) t = new Warbot();
        else if (r == 2) t = new Martian();
        else if (r == 3) t = new Lizardfolk();
        else t = new UncontrolledRover();
        
        // scale threat level
        t.scale(Storage.getCompletedMissions(context));
        return t;
    }

    public List<Threat> generateMissionThreats(Context context, int difficulty, int count) {
        List<Threat> saved = Storage.loadCurrentThreats(context);
        if (saved != null && !saved.isEmpty()) {
            this.currentThreats = saved;
        } else {
            currentThreats.clear();
            for (int i = 0; i < count; i++) currentThreats.add(createMission(context));
            Storage.saveCurrentThreats(context, currentThreats);
        }
        return currentThreats;
    }

    public void setSquad(CrewMember[] slots) {
        currentSquad.clear();
        for (CrewMember m : slots) if (m != null) currentSquad.add(m);
    }

    public List<CrewMember> getCurrentSquad() { return currentSquad; }
    public List<Threat> getCurrentThreats() { return currentThreats; }
}
