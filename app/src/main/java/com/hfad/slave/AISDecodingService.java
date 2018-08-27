package com.hfad.slave;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import static com.hfad.slave.AIVDM.strbuildtodec;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class AISDecodingService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private Handler handler;
    private String packet;
    private Callbacks sCallbacks;
    private AIVDM aivdmObj;
    private PostnReportClassA posObjA;
    private PostnReportClassB posObjB;
    private StaticVoyageData voyageDataObj;
    private StaticDataReport dataReportObj;

    //Data to be decoded
    private long recvdMMSI;
    private double recvdLat;
    private double recvdLon;
    private int recvdSpeed;
    private int recvdCourse;
    private int recvdTimeStamp;
    private String recvdStationName;

    public AISDecodingService() {
        super("AISDecodingService");
        aivdmObj = new AIVDM();
        posObjA = new PostnReportClassA(); //1,2,3
        posObjB = new PostnReportClassB(); //18
        voyageDataObj = new StaticVoyageData(); //5
        dataReportObj = new StaticDataReport(); //24
    }

    //Declare Interface
    public interface Callbacks{
        public String onPacketReceived();
    }


    public int onStartCommand(Intent intent, int flags, int startId)
    {
        handler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this){
            try{
                wait(10000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        try
        {
            SQLiteOpenHelper aisDecoderDatabaseHelper = new AISDecoderDatabaseHelper(this);
            SQLiteDatabase db = aisDecoderDatabaseHelper.getReadableDatabase();
            Cursor cursor_stnlist = db.query("AISSTATIONLIST",
                    new String[] {"MMSI", "AIS_STATION_NAME"},
                    null,
                    null,
                    null, null, null);

            Cursor cursor_fixedstnlist = db.query("AISFIXEDSTATIONPOSITION",
                    null,
                    null,
                    null,
                    null, null, null);

            Cursor cursor_mobilestnlist = db.query("AISMOBILESTATION",
                    null,
                    null,
                    null,
                    null, null, null);

            String[] dataExtr = sCallbacks.onPacketReceived().split(",");
            aivdmObj.setData(dataExtr);
            StringBuilder binary = aivdmObj.decodePayload();
            int num = (int)strbuildtodec(0,5,6,binary,int.class);
            msgDecoding(num, binary);

            if(cursor_stnlist.moveToFirst())
            {
                do{
                    long mmsi = cursor_stnlist.getLong(0);
                    String aisStnName = cursor_stnlist.getString(1);

                    //Compared with the MMSI and AISStationName received from the WiFi
                    //This needs to be implemented in Wifi Service
                    //Decoding logic
                    //String packet = "!AIVDM,1,1,1,B,15UDQt001aPT136NlWiD93E20<<P,0*30"; //will be received from Wifi Service

                    /*Log.d("decoded", String.valueOf(posObjA.getMsgInd()));
                    Log.d("decoded", String.valueOf(posObjA.getRepeatInd()));
                    Log.d("decoded", String.valueOf(posObjA.getMMSI()));
                    Log.d("decoded", String.valueOf(posObjA.getStatus()));
                    Log.d("decoded", String.valueOf(posObjA.getTurn()));
                    Log.d("decoded", String.valueOf(posObjA.getSpeed()));
                    Log.d("decoded", String.valueOf(posObjA.getAccuracy()));
                    Log.d("decoded", String.valueOf(posObjA.getLongitude()));
                    Log.d("decoded", String.valueOf(posObjA.getLatitude()));
                    Log.d("decoded", String.valueOf(posObjA.getCourse()));
                    Log.d("decoded", String.valueOf(posObjA.getHeading()));
                    Log.d("decoded", String.valueOf(posObjA.getSeconds()));
                    Log.d("decoded", String.valueOf(posObjA.getManeuver()));
                    Log.d("decoded", String.valueOf(posObjA.getRaim()));
                    Log.d("decoded", String.valueOf(posObjA.getRadio()));*/
                    if(recvdMMSI == mmsi){

                            //Writing to the database table AISFIXEDSTATIONPOSITION
                            //More fields to be included
                            cursor_fixedstnlist.moveToFirst();
                            do{
                               if((cursor_fixedstnlist.getLong(0)) == mmsi) {
                                   ContentValues decodedValues = new ContentValues();
                                   decodedValues.put("AIS_STATION_NAME", recvdStationName);
                                   if((num != 5) && (num != 24)) {
                                       decodedValues.put("LATITUDE", recvdLat);
                                       decodedValues.put("LONGITUDE", recvdLon);
                                       decodedValues.put("SOG", recvdSpeed);
                                       decodedValues.put("COG", recvdCourse);
                                       decodedValues.put("TIME_STAMP", recvdTimeStamp);
                                       decodedValues.put("ISPOSITIONPREDICTED", 0);
                                   }
                                   db.update("AISFIXEDSTATIONPOSITION", decodedValues, null, null);
                                   break;
                                }
                            }while(cursor_fixedstnlist.moveToNext());

                            if(!cursor_fixedstnlist.moveToNext()) {
                                //Writing to the database table AISFIXEDSTATIONPOSITION
                                ContentValues decodedValues = new ContentValues();
                                decodedValues.put("AIS_STATION_NAME", recvdStationName);
                                if((num != 5) && (num != 24)) {
                                    decodedValues.put("LATITUDE", recvdLat);
                                    decodedValues.put("LONGITUDE", recvdLon);
                                    decodedValues.put("SOG", recvdSpeed);
                                    decodedValues.put("COG", recvdCourse);
                                    decodedValues.put("TIME_STAMP", recvdTimeStamp);
                                    decodedValues.put("ISPOSITIONPREDICTED", 0);
                                }
                                db.insert("AISFIXEDSTATIONPOSITION", null, decodedValues);
                            }
                    }
                    else{
                            cursor_mobilestnlist.moveToFirst();
                            do{
                                if((cursor_mobilestnlist.getLong(0)) == mmsi) {
                                    ContentValues decodedValues = new ContentValues();
                                    decodedValues.put("AIS_STATION_NAME", recvdStationName);
                                    if((num != 5) && (num != 24)) {
                                        decodedValues.put("LATITUDE", recvdLat);
                                        decodedValues.put("LONGITUDE", recvdLon);
                                        decodedValues.put("SOG", recvdSpeed);
                                        decodedValues.put("COG", recvdCourse);
                                        decodedValues.put("TIME_STAMP", recvdTimeStamp);
                                        decodedValues.put("ISPOSITIONPREDICTED", 0);
                                    }
                                    db.update("AISMOBILESTATION", decodedValues, null, null);
                                    break;
                                }
                            }while(cursor_fixedstnlist.moveToNext());

                            if(!cursor_mobilestnlist.moveToNext()) {
                                //Writing to the database table AISMOBILESTATION
                                ContentValues decodedValues = new ContentValues();
                                decodedValues.put("AIS_STATION_NAME", recvdStationName);
                                if((num != 5) && (num != 24)) {
                                    decodedValues.put("LATITUDE", recvdLat);
                                    decodedValues.put("LONGITUDE", recvdLon);
                                    decodedValues.put("SOG", recvdSpeed);
                                    decodedValues.put("COG", recvdCourse);
                                    decodedValues.put("TIME_STAMP", recvdTimeStamp);
                                    decodedValues.put("ISPOSITIONPREDICTED", 0);
                                }
                                db.insert("AISMOBILESTATION", null, decodedValues);
                            }
                    }

                }while(cursor_stnlist.moveToNext());

            }


            cursor_stnlist.close();
            cursor_fixedstnlist.close();
            cursor_mobilestnlist.close();
            db.close();
        }catch (SQLException e)
        {
            String text = "Database unavailable";
            showText(text);
        }
    }

    private void msgDecoding(int num, StringBuilder binary){


        switch(num)
        {
            case 1 :
            case 2 :
            case 3 :
                posObjA.setData(binary);
                recvdMMSI = posObjA.getMMSI();
                recvdLat = posObjA.getLatitude();
                recvdLon = posObjA.getLongitude();
                recvdSpeed = posObjA.getSpeed();
                recvdCourse = posObjA.getCourse();
                recvdTimeStamp = posObjA.getSeconds();
                break;
            case 5:
                voyageDataObj.setData(binary);
                recvdMMSI = voyageDataObj.getMMSI();
                recvdStationName = voyageDataObj.getVesselName();
                break;
            case 18:
                posObjB.setData(binary);
                recvdMMSI = posObjB.getMMSI();
                recvdLat = posObjB.getLatitude();
                recvdLon = posObjB.getLongitude();
                recvdSpeed = posObjB.getSpeed();
                recvdCourse = posObjB.getCourse();
                recvdTimeStamp = posObjB.getSeconds();
                break;
            case 24:
                dataReportObj.setData(binary);
                recvdMMSI = dataReportObj.getMMSI();
                recvdStationName = dataReportObj.getVesselName();
                break;
            default:
                recvdMMSI = 0;
                recvdLat = 0;
                recvdLon = 0;
                break;

        }


    }

    private void showText(final String text) {

        handler.post(new Runnable(){
            public void run()
            {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
        });

    }
}
