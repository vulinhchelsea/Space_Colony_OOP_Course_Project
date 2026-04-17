package com.example.spacecolony.core;

import com.example.spacecolony.crewmembers.CrewMember;
import com.example.spacecolony.threats.Threat;
import java.util.List;

public class CombatManager {
    private final List<CrewMember> squad;
    private final List<Threat> threats;
    private int round = 1;

    public CombatManager(List<CrewMember> squad, List<Threat> threats) {
        this.squad = squad;
        this.threats = threats;
    }

    public String executeAttack(Entity attacker, Entity defender) {
        int baseSkill = attacker.getSkill();
        boolean isWeakness = false;
        
        // x1.5 damage if attacking weakness
        if (attacker instanceof CrewMember && defender instanceof Threat) {
            if (((Threat) defender).getWeakness().equalsIgnoreCase(attacker.getType())) {
                baseSkill = (int) (baseSkill * 1.5);
                isWeakness = true;
            }
        }
        
        int damage = baseSkill - defender.getResilience();
        if (damage < 1) damage = 1;
        
        defender.loseEnergy(damage);
        // log attack
        String log = String.format("%s attacks %s for %d damage!", attacker.getName(), defender.getName(), damage);
        if (isWeakness) log += " (WEAKNESS EXPLOITED!)";
        return log;
    }

    public boolean isVictory() {
        for (Threat t : threats) if (!t.isDefeated()) return false;
        return true;
    }

    public boolean isDefeat() {
        for (CrewMember c : squad) if (!c.isDefeated()) return false;
        return true;
    }

    public void rewardCrew() {
        for (CrewMember c : squad) if (!c.isDefeated()) c.gainExp(50);
    }

    public void nextRound() { round++; }
    public int getRound() { return round; }
    public List<CrewMember> getSquad() { return squad; }
    public List<Threat> getThreats() { return threats; }
}
