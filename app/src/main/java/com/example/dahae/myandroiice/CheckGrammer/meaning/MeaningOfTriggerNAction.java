package com.example.dahae.myandroiice.CheckGrammer.meaning;

/**
 * Created by b_newyork on 2016-01-19.
 */
public class MeaningOfTriggerNAction {

    public String triggerConstant(String trigger) {
        String meaning = "-1";

//        if (trigger.equals("WifiOn"))
//            meaning = "2001";
//        else if (trigger.equals("WifiOff"))
//            meaning = "2002";
//        else if (trigger.equals("Sound"))
//            meaning = "2003";
//        else if (trigger.equals("Vibration"))
//            meaning = "2004";
//        else if (trigger.equals("Silence"))
//            meaning = "2005";
//        else if (trigger.equals("DataOn"))
//            meaning = "2006";
//        else if (trigger.equals("DataOff"))
//            meaning = "2007";
//        else if (trigger.equals("BluetoothOn"))
//            meaning = "2008";
//        else if (trigger.equals("BluetoothOff"))
//            meaning = "2009";
//        else if (trigger.equals("ScreenOn"))
//            meaning = "2010";
//        else if (trigger.equals("ScreenOff"))
//            meaning = "1";
//        else if (trigger.equals("AirplaneModeOff"))
//            meaning = "2";
//        else if (trigger.equals("AirplaneModeOn"))
//            meaning = "2";
//        else if (trigger.equals("SMSreceiver"))
//            meaning = "2011";
//        else if (trigger.equals("CallEnded"))
//            meaning = "2012";
//        else if (trigger.equals("CallReception"))
//            meaning = "3";
//        else if (trigger.equals("PowerConnected"))
//            meaning = "2013";
//        else if (trigger.equals("PowerDisConnected"))
//            meaning = "2014";
//        else if (trigger.equals("EarphoneIn"))
//            meaning = "2015";
//        else if (trigger.equals("EarphoneOut"))
//            meaning = "2016";
//        else if (trigger.equals("LowBattery"))
//            meaning = "2017";
//        else if (trigger.equals("FullBattery"))
//            meaning = "2018";
//        else if (trigger.equals("UpsideDown"))
//            meaning = "4";
//        else if (trigger.equals("SensorLR"))
//            meaning = "2019";
//        else if (trigger.equals("SensorUPDOWN"))
//            meaning = "2020";
//        else if (trigger.equals("SensorBright"))
//            meaning = "2021";
//        else if (trigger.equals("SensorClose"))
//            meaning = "2022";
//        else if (trigger.equals("Location"))
//            meaning = "2023";
//        else if (trigger.equals("Time"))
//            meaning = "4";

       if (trigger.contains("ScreenOff"))
            meaning = "1";
       else if (trigger.contains("AirplaneModeOff"))
            meaning = "2";
       else if (trigger.contains("AirplaneModeOn"))
            meaning = "2";

       else if (trigger.contains("CallReception"))
            meaning = "3";

       else if (trigger.contains("UpsideDown"))
            meaning = "4";
       else if (trigger.contains("Time"))
            meaning = "4";

        return meaning;
    }

    public String actionConstant(String action) {
        String meaning = "-1";

        if (action.contains("WifiOn"))
            meaning = "2";
        else if (action.contains("WifiOff"))
            meaning = "2";

        else if (action.contains("Silence"))
            meaning = "2";
        else if (action.contains("DataOn"))
            meaning = "2";
        else if (action.contains("DataOff"))
            meaning = "2";

        else if (action.contains("Call"))
            meaning = "2.4";
        else if (action.contains("SendingSMS"))
            meaning = "2";
        else if (action.contains("Bookmark"))
            meaning = "1.3";
        else if (action.contains("HomeScreen"))
            meaning = "1.3";


//        if (trigger.equals("WifiOn"))
//            meaning = "2";
//        else if (trigger.equals("WifiOff"))
//            meaning = "2";
//        else if (trigger.equals("Sound"))
//            meaning = "3001";
//        else if (trigger.equals("Vibration"))
//            meaning = "3002";
//        else if (trigger.equals("Silence"))
//            meaning = "2";
//        else if (trigger.equals("DataOn"))
//            meaning = "2";
//        else if (trigger.equals("DataOff"))
//            meaning = "2";
//        else if (trigger.equals("BluetoothOn"))
//            meaning = 3003;
//        else if (trigger.equals("BluetoothOff"))
//            meaning = 3004;
//
//        if (trigger.equals("TellPhoneNum"))
//            meaning = 3005;
//        else if (trigger.equals("TellSMS"))
//            meaning = 3006;
//        else if (trigger.equals("TextToVoice"))
//            meaning = 3007;
//        else if (trigger.equals("Camera"))
//            meaning = 3000;
//        else if (trigger.equals("Flash"))
//            meaning = 3008 ;
//        else if (trigger.equals("AudioRecorder"))
//            meaning = 3009;
//        else if (trigger.equals("VolumeRing"))
//            meaning = 3010;
//        else if (trigger.equals("VolumeMusic"))
//            meaning = 3011;
//        else if (trigger.equals("Call"))
//            meaning = 24;
//        else if (trigger.equals("SendingSMS"))
//            meaning = 2;
//        else if (trigger.equals("Notification"))
//            meaning = 3012;
//        else if (trigger.equals("Bookmark"))
//            meaning = 13;
//        else if (trigger.equals("HomeScreen"))
//            meaning = 13;
//        else if (trigger.equals("Plantrue"))
//            meaning = 3013;
//        else if (trigger.equals("Planflase"))
//            meaning = 3014;

        return meaning;
    }
}
