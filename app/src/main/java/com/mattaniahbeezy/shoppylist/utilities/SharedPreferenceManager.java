package com.mattaniahbeezy.shoppylist.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

/**
 * Created by Beezy Works Studios on 8/23/2017.
 */

class SharedPreferenceManager {
    private static final SharedPreferenceManager ourInstance = new SharedPreferenceManager();
    private SharedPreferences sharedPreferences;
    static final String CACHED_PRODUCTS_KEY = "key_cached_products";

    static SharedPreferenceManager getInstance() {
        return ourInstance;
    }

    private SharedPreferenceManager() {
    }

    void setContext(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    String getString(String key, @Nullable String defaultValue) {
        if (defaultValue == null) {
            defaultValue = "";
        }
        return this.sharedPreferences.getString(key, defaultValue);
    }

    void setString(String key, String value) {
        this.sharedPreferences.edit().putString(key, value).apply();
    }
}
