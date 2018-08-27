package com.hfad.slave;

import static com.hfad.slave.AIVDM.strbuildtodec;

public class PostnReportClassA {

    private int msgInd;
    private int repeatInd;
    private long mmsi;
    private int status;
    private int turn;
    private int speed;
    private boolean accuracy;
    private double lon;
    private double lat;
    private int course;
    private int heading;
    private int sec;
    private int maneuver;
    private boolean raim;
    private long radio;

    public PostnReportClassA()
    {
//		System.out.println(bin);
        msgInd = -1;
        repeatInd = -1;
        mmsi =  -1;
        status = -1;
        turn = -1;
        speed = -1;
        accuracy = false;
        lon = -1;
        lat = -1;
        course = -1;
        heading = -1;
        sec = -1;
        maneuver = -1;
        raim = false;
        radio = -1;

    }

    public int getMsgInd()
    {
        return msgInd;
    }

    public int getRepeatInd()
    {
        return repeatInd;
    }

    public long getMMSI()
    {
        return mmsi;
    }

    public int getStatus()
    {
        return status;
    }

    public int getTurn()
    {
        return turn;
    }

    public int getSpeed()
    {
        return speed;
    }

    public boolean getAccuracy()
    {
        return accuracy;
    }

    public double getLongitude()
    {
        return lon;
    }

    public double getLatitude()
    {
        return lat;
    }

    public int getCourse()
    {
        return course;
    }

    public int getHeading()
    {
        return heading;
    }

    public int getSeconds()
    {
        return sec;
    }

    public int getManeuver()
    {
        return maneuver;
    }

    public boolean getRaim()
    {
        return raim;
    }

    public long getRadio()
    {
        return radio;
    }

    public void setData(StringBuilder bin)
    {
//		System.out.println(bin);
        msgInd = (int)strbuildtodec(0,5,6,bin,int.class);
        repeatInd = (int)strbuildtodec(6,7,2,bin,int.class);
        mmsi =  (long)strbuildtodec(8,37,30,bin,long.class);
        status = (int)strbuildtodec(38,41,4,bin,int.class);
        turn = (int)strbuildtodec(42,49,8,bin,int.class);
        speed = (int)strbuildtodec(50,59,10,bin,int.class);
        accuracy = (boolean)strbuildtodec(60,60,1,bin,int.class);
        lon = (long)strbuildtodec(61,88,28,bin,long.class)/600000.0;
        lat = (long)strbuildtodec(89,115,27,bin,long.class)/600000.0;
        course = (int)strbuildtodec(116,127,12,bin,int.class);
        heading = (int)strbuildtodec(128,136,9,bin,int.class);
        sec = (int)strbuildtodec(137,142,6,bin,int.class);
        maneuver = (int)strbuildtodec(143,144,2,bin,int.class);
        raim = (boolean)strbuildtodec(148,148,1,bin,int.class);
        radio = (long)strbuildtodec(149,167,19,bin,long.class);
    }


};
