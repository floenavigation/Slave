package com.hfad.slave;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AISDecoderDatabaseHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "floeNaviDatabase";
    private static final int DB_VERSION = 1;

    AISDecoderDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(oldVersion < 1)
        {
            db.execSQL("CREATE TABLE AISSTATIONLIST (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "MMSI INTEGER,"
                    + "AIS_STATION_NAME TEXT);");

            db.execSQL("CREATE TABLE AISFIXEDSTATIONPOSITION (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "MMSI INTEGER,"
                    + "AIS_STATION_NAME TEXT,"
                    + "LATITUDE REAL,"
                    + "LONGITUDE REAL,"
                    + "X_POSITION INTEGER,"
                    + "Y_POSITION INTEGER,"
                    + "SOG REAL,"
                    + "COG REAL,"
                    + "DEVICE_TYPE TEXT,"
                    + "TIME_STAMP REAL,"
                    + "ISPOSITIONPREDICTED NUMERIC,"
                    + "PREDICTIONACCURACY INTEGER);");

            db.execSQL("CREATE TABLE AISMOBILESTATION (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "MMSI INTEGER,"
                    + "AIS_STATION_NAME TEXT,"
                    + "LATITUDE REAL,"
                    + "LONGITUDE REAL,"
                    + "X_POSITION INTEGER,"
                    + "Y_POSITION INTEGER,"
                    + "DEVICE_TYPE TEXT,"
                    + "TIME_STAMP REAL);");
        }

    }
}
