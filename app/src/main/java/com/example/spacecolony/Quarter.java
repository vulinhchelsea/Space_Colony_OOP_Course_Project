package com.example.spacecolony;

import java.util.ArrayList;

public class Quarter {
    private int pieceCount;
    private ArrayList<CrewMember> members = new ArrayList<>();

    public void createCrewMember(CrewMember cm) {
        members.add(cm);
    }

    public void restoreEnergy(CrewMember cm) {
        cm.setEnergy(cm.getMaxEnergy());
    }

    public int countCrewMember() {
        return members.size();
    }

    public void resetCrewMember(CrewMember cm) {
        cm.resetMember();
    }
}
