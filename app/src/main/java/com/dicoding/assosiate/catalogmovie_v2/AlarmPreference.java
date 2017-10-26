package com.dicoding.assosiate.catalogmovie_v2;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Djaka on 26/10/2017.
 */

public class AlarmPreference {
    private final String PREF_NAME = "MoviePreference";
    private final String KEY_REPEATING_TIME = "repeatingTime";
    private final String KEY_MOVIE_REMINDER = "MovieReminder";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    public AlarmPreference(Context context){
        mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public String getRepeatingTime(){
        return mSharedPreferences.getString(KEY_REPEATING_TIME, null);
    }

    public void setRepeatingTime(String time){
        editor.putString(KEY_REPEATING_TIME, time);
        editor.commit();
    }

    public String getRepeatingMessage(){
        return mSharedPreferences.getString(KEY_MOVIE_REMINDER, null);
    }

    public void setRepeatingMessage(String message){
        editor.putString(KEY_MOVIE_REMINDER, message);
        editor.commit();
    }
}
