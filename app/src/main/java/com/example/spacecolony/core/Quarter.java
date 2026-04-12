package com.example.spacecolony.core;

import android.content.Context;

import com.example.spacecolony.crewmembers.CrewMember;
import com.example.spacecolony.crewmembers.Engineer;
import com.example.spacecolony.crewmembers.Medic;
import com.example.spacecolony.crewmembers.Pilot;
import com.example.spacecolony.crewmembers.Scientist;
import com.example.spacecolony.crewmembers.Soldier;
import com.example.spacecolony.util.Storage;

import java.util.Random;


public class Quarter {
    private int pieceCount;

    // create crew member
    public void createCrewMember(Context context, CrewMember cm) {
        Storage.addCrewMember(context, cm);
    }

    public void restoreEnergy(CrewMember cm) {
        cm.setEnergy(cm.getMaxEnergy());
    }

    // count crew member
    public int countCrewMember(Context context) {
        return Storage.countCrewMembers(context);
    }
    public void resetCrewMember(CrewMember cm) {
        cm.resetMember();
    }

    // generate random crew for recruitment
    public CrewMember generateRandomCandidate() {
        String[] names = {"Linh Vu", "Hai Anh", "Kevin"};
        Random random = new Random();

        String name = names[random.nextInt(names.length)] + " " + random.nextInt(100);
        
        int typeChoice = random.nextInt(5);
        switch (typeChoice) {
            case 0: return new Medic(name);
            case 2: return new Engineer(name);
            case 3: return new Scientist(name);
            case 4: return new Pilot(name);
            default: return new Soldier(name);
        }
    }
}
