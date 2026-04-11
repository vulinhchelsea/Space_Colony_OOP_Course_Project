package com.example.spacecolony;

public class Threat extends Entity{
    String weakness;
    int piece;

    public Threat(String name, String type, int skill, int resilience, int maxEnergy, String weakness, int piece){
        super(name, type, skill, resilience, maxEnergy);
        this.weakness = weakness;
        this.piece = piece;
    }
    public void attack(CrewMember target){
        int damage = getSkill() - target.getResilience();
        if (damage<0) damage = 0;
        target.loseEnergy(damage);
    }
}
