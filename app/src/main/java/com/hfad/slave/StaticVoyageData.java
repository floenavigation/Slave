package com.hfad.slave;


import static com.hfad.slave.AIVDM.strbuildtodec;

public class StaticVoyageData {

    private int msgInd;
    private int repeatInd;
    private long mmsi;
    private int aisVersion;
    private int imoNumber;
    private String callSign;
    private String vesselName;
    private int shipType;
    private int dimToBow;
    private int dimToStern;
    private int dimToPort;
    private int dimToStarBoard;
    private int postnFixType;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int draught;
    private String destn;
    private boolean dte;
    private int reserved;

    public StaticVoyageData()
    {

        msgInd = -1;
        repeatInd = -1;
        mmsi =  -1;
        aisVersion = -1;
        imoNumber = -1;
        callSign = "";
        vesselName = "";
        shipType = -1;
        dimToBow = -1;
        dimToStern = -1;
        dimToPort = -1;
        dimToStarBoard = -1;
        postnFixType = -1;
        month = -1;
        day = -1;
        hour = -1;
        minute = -1;
        month = -1;
        draught = -1;
        destn = "";
        dte = false;
        reserved = -1;
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

    public int getAisVersion()
    {
        return aisVersion;
    }

    public int getImoNumber()
    {
        return imoNumber;
    }

    public String getCallSign()
    {
        return callSign;
    }

    public String getVesselName()
    {
        return vesselName;
    }

    public int getShipType()
    {
        return shipType;
    }

    public int getDimToBow()
    {
        return dimToBow;
    }

    public int getDimToStern()
    {
        return dimToStern;
    }

    public int getDimToPort()
    {
        return dimToPort;
    }

    public int getDimToStarBoard()
    {
        return dimToStarBoard;
    }

    public int getPostnFixType()
    {
        return postnFixType;
    }

    public int getMonth()
    {
        return month;
    }

    public int getDay()
    {
        return day;
    }

    public int getHour()
    {
        return hour;
    }

    public int getMinute()
    {
        return minute;
    }

    public int getDraught()
    {
        return draught;
    }

    public String getDestn()
    {
        return destn;
    }

    public boolean getDte()
    {
        return dte;
    }

    public int getReserved()
    {
        return reserved;
    }

    public void setData(StringBuilder bin)
    {

        msgInd = (int)strbuildtodec(0,5,6,bin,int.class);
        repeatInd = (int)strbuildtodec(6,7,2,bin,int.class);
        mmsi =  (long)strbuildtodec(8,37,30,bin,long.class);
        aisVersion = (int)strbuildtodec(38,39,2,bin,int.class);
        imoNumber = (int)strbuildtodec(40,69,30,bin,int.class);
        callSign = (String) strbuildtodec(70,111,42,bin,String.class);
        vesselName = (String)strbuildtodec(112,231,120,bin,String.class);
        shipType = (int)strbuildtodec(232,239,8,bin,int.class);
        dimToBow = (int)strbuildtodec(240,248,9,bin,int.class);
        dimToStern = (int)strbuildtodec(249,257,9,bin,int.class);
        dimToPort = (int)strbuildtodec(258,263,6,bin,int.class);
        dimToStarBoard = (int)strbuildtodec(264,269,6,bin,int.class);
        postnFixType = (int)strbuildtodec(270,273,4,bin,int.class);
        month = (int)strbuildtodec(274,277,4,bin,int.class);
        day = (int)strbuildtodec(278,282,5,bin,int.class);
        hour = (int)strbuildtodec(283,287,5,bin,int.class);
        minute = (int)strbuildtodec(288,293,6,bin,int.class);
        draught = (int)strbuildtodec(294,301,8,bin,int.class);
        destn = (String) strbuildtodec(302,421,120,bin,String.class);
        dte = (boolean)strbuildtodec(422,422,1,bin,int.class);
        reserved = (int)strbuildtodec(423,423,1,bin,int.class);
    }




};
