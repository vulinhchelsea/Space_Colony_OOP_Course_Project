package com.example.spacecolony.core;

import android.content.Context;
import com.example.spacecolony.crewmembers.*;
import com.example.spacecolony.util.Storage;
import java.util.Random;

public class Quarter {
    public void recruitMember(Context c, CrewMember m) { Storage.addCrewMember(c, m); }
    
    public CrewMember generateRandomCandidate() {
        String[] names = {"Nova", "Rex", "Aria", "Kael", "Luna", "Silas", "Zane"};
        Random rand = new Random();
        String name = names[rand.nextInt(names.length)] + "-" + (rand.nextInt(900) + 100);
        
        int r = rand.nextInt(5);
        if (r == 0) return new Medic(name);
        if (r == 1) return new Pilot(name);
        if (r == 2) return new Engineer(name);
        if (r == 3) return new Scientist(name);
        return new Soldier(name);
    }
}
