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

    public class Pilot extends CrewMember{
        public Pilot(String name){
            super(name,"Pilot",5,4,20,"AbilityA");
        }
    }

    public class Engineer extends CrewMember{
        public Engineer(String name){
            super(name,"Engineer",6,3,19,"AbilityB");
        }
    }

    public class Medic extends CrewMember{
        public Medic(String name){
            super(name,"Medic",7,2,18,"AbilityC");
        }
    }

    public class Scientist extends CrewMember{
        public Scientist(String name){
            super(name,"Scientist",8,1,17,"AbilityD");
        }
    }

    public class Soldier extends CrewMember{
        public Soldier(String name){
            super(name,"Soldier",9,0,16,"AbilityE");
        }
    }
}
