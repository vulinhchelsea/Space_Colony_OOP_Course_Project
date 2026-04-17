package com.example.spacecolony.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.spacecolony.crewmembers.*;
import com.example.spacecolony.threats.Threat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final String STORAGE = "ColonyStorage";
    private static final String KEY_MISSIONS = "completed_missions";
    private static final String KEY_RECRUITED = "total_recruited";
    private static final String KEY_THREATS = "current_threats";
    private static final String KEY_CREW = "crew_list";
    
    // cache list in memory
    private static List<CrewMember> cachedCrew;

    private static SharedPreferences getPrefs(Context c) {
        return c.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }

    // crew management
    public static List<CrewMember> loadCrewMembers(Context c) {
        // return from memory if available
        if (cachedCrew != null) return cachedCrew;
        
        String json = getPrefs(c).getString(KEY_CREW, null);
        if (json == null) {
            cachedCrew = initTeam(c);
            return cachedCrew;
        }
        cachedCrew = new Gson().fromJson(json, new TypeToken<ArrayList<CrewMember>>() {}.getType());
        if (cachedCrew == null || cachedCrew.isEmpty()) cachedCrew = initTeam(c);
        return cachedCrew;
    }

    private static List<CrewMember> initTeam(Context c) {
        List<CrewMember> list = new ArrayList<>();
        list.add(new Medic("Hai Anh"));
        list.add(new Soldier("Kaiwen Kang"));
        saveCrewMembers(c, list);
        return list;
    }

    public static void saveCrewMembers(Context c, List<CrewMember> list) {
        cachedCrew = list; // update cache
        getPrefs(c).edit().putString(KEY_CREW, new Gson().toJson(list)).apply();
    }

    // recruitment management
    public static void addCrewMember(Context c, CrewMember m) {
        List<CrewMember> list = loadCrewMembers(c);
        list.add(m);
        saveCrewMembers(c, list);
        getPrefs(c).edit().putInt(KEY_RECRUITED, getTotalRecruited(c) + 1).apply();
    }
    // counting for records
    public static int getTotalRecruited(Context c) { return getPrefs(c).getInt(KEY_RECRUITED, 0); }
    public static int getCompletedMissions(Context c) { return getPrefs(c).getInt(KEY_MISSIONS, 0); }

    public static void finishMission(Context c) {
        getPrefs(c).edit().putInt(KEY_MISSIONS, getCompletedMissions(c) + 1).remove(KEY_THREATS).apply();
    }
    // threats management
    public static void saveCurrentThreats(Context c, List<Threat> t) {
        getPrefs(c).edit().putString(KEY_THREATS, new Gson().toJson(t)).apply();
    }

    public static List<Threat> loadCurrentThreats(Context c) {
        String json = getPrefs(c).getString(KEY_THREATS, null);
        if (json == null) return null;
        return new Gson().fromJson(json, new TypeToken<ArrayList<Threat>>() {}.getType());
    }

    // clearing data
    public static void clearData(Context c) {
        cachedCrew = null; // clear cache
        getPrefs(c).edit().clear().apply();
        c.getSharedPreferences("simulator_prefs", Context.MODE_PRIVATE).edit().clear().apply();
    }
}
