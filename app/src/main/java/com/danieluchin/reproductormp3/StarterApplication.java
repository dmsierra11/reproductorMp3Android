package com.danieluchin.reproductormp3;

import android.app.Application;
import android.content.Context;

/**
 * Created by danielsierraf on 12/16/16.
 */
public class StarterApplication extends Application{
    // Singleton instance
    private static StarterApplication sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        // Setup singleton instance
        sInstance = this;
    }

    // Getter to access Singleton instance
    public static StarterApplication getInstance() {
        return sInstance ;
    }
}
