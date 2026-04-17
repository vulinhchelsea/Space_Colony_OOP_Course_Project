package com.example.spacecolony.crewmembers;

import com.example.spacecolony.R;
import com.example.spacecolony.core.Entity;

public class CrewMember extends Entity {
    private int exp = 0;
    private int level = 1;
    private String specialAbility;

    public CrewMember(String name, String type, int skill, int resilience, int maxEnergy, String specialAbility){
        super(name, type, skill, resilience, maxEnergy);
        this.specialAbility = specialAbility;
    }

    public int getExp() { return exp; }
    public int getLevel() { return level; }

    public void gainExp(int expGain) {
        this.exp += expGain;
        int newLevel = (exp / 100) + 1;
        if (newLevel > level) {
            int gained = newLevel - level;
            level = newLevel;
            // level up logic
            setSkill(getSkill() + 2 * gained);
            setResilience(getResilience() + gained);
            setMaxEnergy(getMaxEnergy() + 10 * gained);
            setEnergy(getMaxEnergy());
        }
    }

    public int getAvatarResource() {
        switch (getType().toLowerCase()) {
            case "medic": return R.drawable.medic;
            case "soldier": return R.drawable.soldier;
            case "pilot": return R.drawable.pilot;
            case "engineer": return R.drawable.engineer;
            case "scientist": return R.drawable.scientist;
            default: return android.R.drawable.ic_menu_gallery;
        }
    }
}
