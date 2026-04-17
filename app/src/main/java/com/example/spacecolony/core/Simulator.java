package com.example.spacecolony.core;

import android.content.Context;
import com.example.spacecolony.util.Storage;

public class Simulator {
    private static final String PREF = "simulator_prefs";
    private static final String KEY_MEDBAY = "medbay_cooldown";
    private static final String KEY_RECRUIT = "recruit_cooldown";
    private static final String KEY_TRAINING = "training_cooldown";
    
    // unified check for cooldown
    public boolean isAvailable(Context c, String key) {
        return Storage.getCompletedMissions(c) >= c.getSharedPreferences(PREF, 0).getInt(key, 0);
    }

    // unified method to start cooldown
    public void useFeature(Context c, String key) {
        c.getSharedPreferences(PREF, 0).edit().putInt(key, Storage.getCompletedMissions(c) + 2).apply();
    }

    public boolean isMedbayAvailable(Context c) {
        return isAvailable(c, KEY_MEDBAY);
    }

    public void useMedbay(Context c) {
        useFeature(c, KEY_MEDBAY);
    }

    public boolean isRecruitAvailable(Context c) {
        return isAvailable(c, KEY_RECRUIT);
    }

    public void useRecruit(Context c) {
        useFeature(c, KEY_RECRUIT);
    }

    public boolean isTrainingAvailable(Context c) {
        return isAvailable(c, KEY_TRAINING);
    }

    public void useTraining(Context c) {
        useFeature(c, KEY_TRAINING);
    }
}
