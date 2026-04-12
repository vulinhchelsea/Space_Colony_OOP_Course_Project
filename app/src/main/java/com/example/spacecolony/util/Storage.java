package com.example.spacecolony.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.spacecolony.crewmembers.CrewMember;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final String STORAGE_NAME = "ColonyStorage";

    // add crew member
    public static void addCrewMember(Context context, CrewMember newMember) {
        List<CrewMember> list = loadCrewMembers(context);
        list.add(newMember);
        saveCrewMembers(context, list);
    }
    // load crew member
    public static List<CrewMember> loadCrewMembers(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("crew_list", null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CrewMember>>() {}.getType();

        List<CrewMember> list = gson.fromJson(json, type);
        return (list != null) ? list : new ArrayList<>();
    }

    // save crew list
    public static void saveCrewMembers(Context context, List<CrewMember> crewList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(crewList);
        editor.putString("crew_list", json);
        editor.apply();
    }

    // member counting
    public static int countCrewMembers(Context context) {
        return loadCrewMembers(context).size();
    }

    // Clear all data for game restart
    public static void clearAllData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}