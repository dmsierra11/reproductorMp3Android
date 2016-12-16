package com.danieluchin.reproductormp3;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;

class Utils {
    private Context mContext;

    static Utils getInstance() {
        return new Utils();
    }

    private Utils() {
        mContext = StarterApplication.getInstance();
    }

    String[] getAssets(){
        AssetManager assetManager = mContext.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        return files;
    }
}
