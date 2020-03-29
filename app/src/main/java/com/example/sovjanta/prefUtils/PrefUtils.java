package com.example.sovjanta.prefUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

public class PrefUtils {

    private static String testData = "test_data";

    public static void saveData(Context context, String testDaTa) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(testData, testDaTa).apply();
    }

    public static void getData(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(testData, null).apply();
    }


    public static String getJsonFromObject(Object object, Class<?> classType) {
        String jsonText = "";
        if (object != null) {
            Gson gson = new Gson();
            jsonText = gson.toJson(object, classType);
        }
        return jsonText;
    }

    public static Object getObjectFromJson(String jsonText, Class<?> classType) {
        Object object = null;
        if (jsonText != null) {
            Gson gson = new Gson();
            object = gson.fromJson(jsonText, classType);
        }
        return object;
    }


    public static void saveProfile(Context context, String profile_str) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString("profile_key", profile_str).apply();

    }

   /* public static ProfileObject getProfile(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        ProfileObject profileObject = null;


        try {

            String profile_str = sp.getString("profile_key", null);
            if (profile_str != null) {
                profileObject = (ProfileObject) getObjectFromJson(profile_str, ProfileObject.class);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return profileObject;

    }
*/
}
