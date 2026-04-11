package com.example.spacecolony;

public class Threat extends Entity{
    String weakness;
    int piece;

    public Threat(String name, String type, int skill, int resilience, int energy, int maxEnergy, String weakness, int piece){
        super(name, type, skill, resilience, energy, maxEnergy);
        this.weakness = weakness;
        this.piece = piece;
    }
    public void attack(CrewMember crewMember){
        crewMember.loseEnergy(getSkill());
    }
}
