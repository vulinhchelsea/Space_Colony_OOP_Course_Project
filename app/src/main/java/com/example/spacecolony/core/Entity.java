package com.example.spacecolony.core;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private String name, type;
    private int skill, resilience, energy, maxEnergy;
    private boolean isDefeated = false;

    public Entity(String name, String type, int skill, int resilience, int maxEnergy) {
        this.name = name; this.type = type; this.skill = skill; this.resilience = resilience;
        this.maxEnergy = maxEnergy; this.energy = maxEnergy;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public int getSkill() { return skill; }
    public void setSkill(int skill) { this.skill = skill; }
    public int getResilience() { return resilience; }
    public void setResilience(int resilience) { this.resilience = resilience; }

    public int getEnergy() { return energy; }

    public void setEnergy(int energy) {
        this.energy = Math.max(0, energy);
        this.isDefeated = (this.energy == 0);
    }
    public int getMaxEnergy() { return maxEnergy; }
    public void setMaxEnergy(int maxEnergy) { this.maxEnergy = maxEnergy; }
    public boolean isDefeated() { return isDefeated; }
    
    // update status
    public void loseEnergy(int damage) { setEnergy(this.energy - damage); }
}
