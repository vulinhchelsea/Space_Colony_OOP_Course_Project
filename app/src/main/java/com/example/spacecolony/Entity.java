package com.example.spacecolony;

import java.io.Serializable;

public abstract class Entity implements Serializable {  // serialize for data storage
    private String name;
    private String type;
    private int skill;
    private int resilience;
    private int energy;
    private int maxEnergy;
    private boolean isDefeated;

    public Entity(String name, String type, int skill, int resilience, int maxEnergy) {
        this.name = name;
        this.type = type;
        this.skill = skill;
        this.resilience = resilience;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;
        this.isDefeated = false;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getSkill(){
        return skill;
    }

    public void setSkill(int skill){
        this.skill = skill;
    }

    public int getResilience(){
        return resilience;
    }

    public void setResilience(int resilience){
        this.resilience = resilience;
    }

    public int getEnergy(){
        return energy;
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }

    public void loseEnergy(int energyLose) {
        this.energy = energy - energyLose;
    }

    public int getMaxEnergy(){
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy){
        this.maxEnergy = maxEnergy;
    }

    public boolean isDefeated(){
        return this.isDefeated;
    }

    public void setDefeated(boolean isDefeated){
        this.isDefeated = isDefeated;
    }

    public String getType(){
        return type;
    }
}
