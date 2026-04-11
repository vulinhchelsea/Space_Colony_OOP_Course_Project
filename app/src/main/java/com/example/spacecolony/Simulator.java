package com.example.spacecolony;

public class Simulator {
    public boolean inCoolDown = false;
    public int useCount = 0;

    public void train(CrewMember cm, int trainValue) {
        if (inCoolDown) {
            //display that it is in cooldown and cannot train until next mission is completed
        }
        else {
            cm.gainExp(trainValue);
            useCount++;
            inCoolDown = true;
        }
    }
}
