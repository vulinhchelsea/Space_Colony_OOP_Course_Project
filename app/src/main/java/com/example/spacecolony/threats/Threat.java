package com.example.spacecolony.threats;

import com.example.spacecolony.R;
import com.example.spacecolony.core.Entity;

public class Threat extends Entity {
    private String weakness;
    private int level = 1;

    public Threat(String name, String type, int skill, int resilience, int maxEnergy, String weakness, int piece){
        super(name, type, skill, resilience, maxEnergy);
        this.weakness = weakness;
    }

    // scale level logic pass to combat manager
    public void scale(int missionCount) {
        this.level = (missionCount / 2) + 1;
        int bonus = level - 1;
        setSkill(getSkill() + bonus * 2);
        setResilience(getResilience() + bonus);
        setMaxEnergy(getMaxEnergy() + bonus * 5);
        setEnergy(getMaxEnergy());
    }

    public int getIconResource() {
        switch (getType()) {
            case "Warbot": return R.drawable.warbot;
            case "Martian": return R.drawable.martian;
            case "Grey Alien": return R.drawable.grey_alien;
            case "Lizardfolk": return R.drawable.lizardfolk;
            case "Uncontrolled Rover": return R.drawable.uncontrolled_rover;
            default: return R.drawable.warbot;
        }
    }

    public String getWeakness() { return weakness; }
    public int getLevel() { return level; }
}
