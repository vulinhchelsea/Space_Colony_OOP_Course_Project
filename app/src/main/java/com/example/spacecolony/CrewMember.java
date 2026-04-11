package com.example.spacecolony;

public class CrewMember extends Entity{
    private int exp;
    private String specialAbility;

    public CrewMember(String name, String type, int skill, int resilience, int maxEnergy, String specialAbility){
        super(name, type, skill, resilience, maxEnergy);
        this.exp = 0;
        this.specialAbility = specialAbility;
    }

    public int getExp(){
        return exp;
    }

    public void gainExp(int expGain) {
        this.exp = exp + expGain;
    }

    public void resetMember(){

    }

    public void useSpecialAbility(){

    }

    public void attack(){

    }
}
