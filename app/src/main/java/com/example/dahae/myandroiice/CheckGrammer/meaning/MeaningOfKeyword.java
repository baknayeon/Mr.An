package com.example.dahae.myandroiice.CheckGrammer.meaning;

/**
 * Created by b_newyork on 2016-01-12.
 */
public class MeaningOfKeyword {

    public int Constant(String trigger) {
        int Constant = 0;

        if (trigger.equals("And"))
            Constant = -1;
        else if ( trigger.equals("Or"))
            Constant = -2;
        else if(trigger.equals("Done"))
            Constant = -3;

        if (trigger.equals("WifiOn"))
            Constant = 1001;
        else if (trigger.equals("WifiOff"))
            Constant = 1002;
        else if (trigger.equals("ScreenOn"))
            Constant = 1;
        else if (trigger.equals("ScreenOff"))
            Constant = 1;
        else if (trigger.equals("Sound"))
            Constant = 1003;
        else if (trigger.equals("Vibration"))
            Constant = 1004;
        else if (trigger.equals("Silence"))
            Constant = 1005;
        else if (trigger.equals("DataOn"))
            Constant = 1006;
        else if (trigger.equals("DataOff"))
            Constant = 1007;
        else if (trigger.equals("BluetoothOn"))
            Constant = 1008;
        else if (trigger.equals("BluetoothOff"))
            Constant = 1009;
        else if (trigger.equals("SMSreceiver"))
            Constant = 1;
        else if (trigger.equals("AirplaneModeOff"))
            Constant = 1010;
        else if (trigger.equals("AirplaneModeOn"))
            Constant = 1011;
        else if (trigger.equals("CallEnded"))
            Constant = 1;
        else if (trigger.equals("CallReception"))
            Constant = 1;
        else if (trigger.equals("PowerConnected"))
            Constant = 2;
        else if (trigger.equals("PowerDisConnected"))
            Constant = 2;
        else if (trigger.equals("EarphoneIn"))
            Constant = 2;
        else if (trigger.equals("EarphoneOut"))
            Constant = 2;
        else if (trigger.equals("LowBattery"))
            Constant = 1012;
        else if (trigger.equals("FullBattery"))
            Constant = 1013;
        else if (trigger.equals("SensorLR"))
            Constant = 1;
        else if (trigger.equals("SensorUPDOWN"))
            Constant = 1;
        else if (trigger.equals("UpsideDown"))
            Constant = 1;
        else if (trigger.equals("SensorBright"))
            Constant = 1;
        else if (trigger.equals("SensorClose"))
            Constant = 1;
        else if (trigger.equals("Location"))
            Constant = 2;
        else if (trigger.equals("Time"))
            Constant = 1;


        if (trigger.equals("TellPhoneNum"))
            Constant = 2;
        else if (trigger.equals("TellSMS"))
            Constant = 2;
        else if (trigger.equals("TextToVoice"))
            Constant = 1014;
        else if (trigger.equals("Camera"))
            Constant = 1;
        else if (trigger.equals("Flash"))
            Constant = 1015 ;
        else if (trigger.equals("AudioRecorder"))
            Constant = 1;
        else if (trigger.equals("VolumeRing"))
            Constant = 1016 ;
        else if (trigger.equals("VolumeMusic"))
            Constant = 1017;
        else if (trigger.equals("Call"))
            Constant = 1;
        else if (trigger.equals("SendingSMS"))
            Constant = 1018;
        else if (trigger.equals("Notification"))
            Constant = 1019;
        else if (trigger.equals("Bookmark"))
            Constant = 1;
        else if (trigger.equals("HomeScreen"))
            Constant = 1;
        else if (trigger.equals("Plantrue"))
            Constant = 1020;
        else if (trigger.equals("Planflase"))
            Constant = 1021;

        return Constant;
    }


    public String Type(String trigger) {
        String meaning = trigger;

        if (trigger.equals("WifiOn"))
            meaning = "Wifi";
        else if (trigger.equals("WifiOff"))
            meaning = "Wifi";
        else if (trigger.equals("ScreenOn"))
            meaning = "Screen";
        else if (trigger.equals("ScreenOff"))
            meaning = "Screen";
        else if (trigger.equals("Sound"))
            meaning = "Ring";
        else if (trigger.equals("Vibration"))
            meaning = "Ring";
        else if (trigger.equals("Silence"))
            meaning = "Ring";
        else if (trigger.equals("DataOn"))
            meaning = "Data";
        else if (trigger.equals("DataOff"))
            meaning = "Data";
        else if (trigger.equals("BluetoothOn"))
            meaning = "Blue";
        else if (trigger.equals("BluetoothOff"))
            meaning = "Blue";
        else if (trigger.equals("AirplaneModeOff"))
            meaning = "AirMode";
        else if (trigger.equals("AirplaneModeOn"))
            meaning = "AirMode";
        else if (trigger.equals("SMSreceiver"))
            meaning = "SMS";
        else if (trigger.equals("CallEnded"))
            meaning = "Call";
        else if (trigger.equals("CallReception"))
            meaning = "Call";
        else if (trigger.equals("PowerConnected"))
            meaning = "Power";
        else if (trigger.equals("PowerDisConnected"))
            meaning = "Power";
        else if (trigger.equals("EarphoneIn"))
            meaning = "Earphone";
        else if (trigger.equals("EarphoneOut"))
            meaning = "Earphone";
        else if (trigger.equals("LowBattery"))
            meaning = "Battery";
        else if (trigger.equals("FullBattery"))
            meaning = "Battery";
        else if (trigger.equals("UpsideDown"))
            meaning = "UpsideS";
        else if (trigger.equals("SensorLR"))
            meaning = "ShakeS";
        else if (trigger.equals("SensorUPDOWN"))
            meaning = "ShakeS";
        else if (trigger.equals("SensorBright"))
            meaning = "SensorS";
        else if (trigger.equals("SensorClose"))
            meaning = "SensorS";
        else if (trigger.equals("Location"))
            meaning = "Location";
        else if (trigger.equals("Time"))
            meaning = "Time";

        if (trigger.equals( "TellPhoneNum"))
            meaning = "Read";
        else if (trigger.equals("TellSMS"))
            meaning = "Read";
        else if (trigger.equals("TextToVoice"))
            meaning = "Read";
        else if (trigger.equals("Camera"))
            meaning = "Camera";
        else if (trigger.equals("Flash"))
            meaning = "Flash" ;
        else if (trigger.equals("AudioRecorder"))
            meaning = "Recorder";
        else if (trigger.equals("VolumeRing"))
            meaning = "VolumeRing" ;
        else if (trigger.equals("VolumeMusic"))
            meaning = "VolumeMusic";
        else if (trigger.equals("Call"))
            meaning = "Call";
        else if (trigger.equals("SendingSMS"))
            meaning = "Call";
        else if (trigger.equals("Notification"))
            meaning = "Notification";
        else if (trigger.equals("Bookmark"))
            meaning = "Bookmark";
        else if (trigger.equals("HomeScreen"))
            meaning = "Bookmark";
        else if (trigger.equals("Plantrue"))
            meaning = "Plantrue";
        else if (trigger.equals("Planflase"))
            meaning = "Planflase";
        return meaning;
    }

}
