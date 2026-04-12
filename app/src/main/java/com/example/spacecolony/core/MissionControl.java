package com.example.spacecolony.core;

import com.example.spacecolony.threats.Threat;
import com.example.spacecolony.crewmembers.CrewMember;
import com.example.spacecolony.threats.AsteroidStorm;
import com.example.spacecolony.threats.Biohazard;
import com.example.spacecolony.threats.Droid;
import com.example.spacecolony.threats.Meltdown;
import com.example.spacecolony.threats.NeuralPulse;

import java.util.ArrayList;
import java.util.Random;

public class MissionControl {
    private ArrayList<Threat> missions = new ArrayList<>();
    private int winCount = 0;
    private Random rand = new Random();

    public Threat createMission() {
        int r = rand.nextInt(5);
        Threat t;
        switch (r) {
            case 0:
                t = new AsteroidStorm();
                break;
            case 1:
                t = new Meltdown();
                break;
            case 2:
                t = new Biohazard();
                break;
            case 3:
                t = new NeuralPulse();
                break;
            default:
                t = new Droid();
                break;
        }
        missions.add(t);
        return t;
    }

    public int missionCount() {
        return missions.size();
    }

    public int missionWinCount() {
        return winCount;
    }

    public void addMembers(CrewMember cm1, CrewMember cm2, CrewMember cm3) {
        System.out.println("Members added: " + cm1.getName() + ", " + cm2.getName() + ", " + cm3.getName());
    }
}
