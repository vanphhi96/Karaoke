package com.example.vanph.karaokemanage.adapter;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by vanph on 07/10/2017.
 */

public class AssetHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "karaoke.db";
    private static final int DATABASE_VERSION = 1;

    public AssetHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
