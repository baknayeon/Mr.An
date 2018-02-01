package com.example.dahae.myandroiice.NewPlan;

public class ChangingName {

    public String Trigger(String English) {
        String result = English;

        if (English.equals("And"))
            result = "AND";
        else if (English.equals("Or"))
            result = "OR";
        else if (English.equals("Done"))
            result = "DONE";
        else if (English.contains("WifiOn"))
            result = "Wi-Fi 켜짐";
        else if (English.contains("WifiOff"))
            result = "Wi-Fi 꺼짐";
        else if (English.contains("ScreenOn"))
            result = "화면 켜짐";
        else if (English.contains("ScreenOff"))
            result = "화면 꺼짐";
        else if (English.contains("Sound"))
            result = "소리모드";
        else if (English.contains("Vibration"))
            result = "진동모드";
        else if (English.contains("Silence"))
            result = "무음모드";
        else if (English.contains("DataOn"))
            result = "데이터 켜짐";
        else if (English.contains("DataOff"))
            result = "데이터 꺼짐";
        else if (English.contains("BluetoothOn"))
            result = "블루투스 켜짐";
        else if (English.contains("BluetoothOff"))
            result = "블루투스 꺼짐";
        else if (English.contains("SMSreceiver"))
            result = "SMS 수신시";
        else if (English.contains("AirplaneModeOff"))
            result = "비행기모드 꺼짐";
        else if (English.contains("AirplaneModeOn"))
            result = "비행기모드 켜짐";
        else if (English.contains("CallEnded"))
            result = "통화 종료시";
        else if (English.contains("CallReception"))
            result = "전화 수신시";
        else if (English.contains("PowerConnected"))
            result = "충전기 연결시";
        else if (English.contains("PowerDisConnected"))
            result = "충전기 해제시";
        else if (English.contains("EarphoneIn"))
            result = "이어폰 연결시";
        else if (English.contains("EarphoneOut"))
            result = "이어폰 해제시";
        else if (English.contains("LowBattery"))
            result = "배터리 N이하";
        else if (English.contains("FullBattery"))
            result = "배터리 N이상";
        else if (English.contains("SensorLR"))
            result = "양쪽 흔들기";
        else if (English.contains("UpsideDown"))
            result = "폰뒤집기";
        else if (English.contains("SensorBright"))
            result = "밝기 센서";
        else if (English.contains("SensorUPDOWN"))
            result = "위아래 흔들기";
        else if (English.contains("SensorClose"))
            result = "근접 센서";
        else if (English.contains("Location"))
            result = "장소";
        else if (English.contains("Time"))
            result = "요일/시간";

        return result;
    }

    public String TriggerToEglish(String Korean){


        String result = Korean;
        if (Korean.equals("AND"))
            result = "And";
        else if (Korean.equals("OR"))
            result = "Or";
        else if (Korean.equals("DONE"))
            result = "Done";
        else if (Korean.contains("Wi-Fi 켜짐"))
            result = "WifiOn";
        else if (Korean.contains("Wi-Fi 꺼짐"))
            result = "WifiOff";
        else if (Korean.contains("화면 켜짐"))
            result = "ScreenOn";
        else if (Korean.contains("화면 꺼짐"))
            result = "ScreenOff";
        else if (Korean.contains("소리모드"))
            result = "Sound";
        else if (Korean.contains("진동모드"))
            result = "Vibration";
        else if (Korean.contains("무음모드"))
            result = "Silence";
        else if (Korean.contains("데이터 켜짐"))
            result = "DataOn";
        else if (Korean.contains("데이터 꺼짐"))
            result = "DataOff";
        else if (Korean.contains("비행기모드 꺼짐"))
            result = "AirplaneModeOff";
        else if (Korean.contains("비행기모드 켜짐"))
            result = "AirplaneModeOn";
        else if (Korean.contains("블루투스 켜짐"))
            result = "BluetoothOn";
        else if (Korean.contains("블루투스 꺼짐"))
            result = "BluetoothOff";
        else if (Korean.contains("SMS 수신시"))
            result = "SMSreceiver";
        else if (Korean.contains("통화 종료시"))
            result = "CallEnded";
        else if (Korean.contains("전화 수신시"))
            result = "CallReception";
        else if (Korean.contains("충전기 연결시"))
            result = "PowerConnected";
        else if (Korean.contains("충전기 해제시"))
            result = "PowerDisConnected";
        else if (Korean.contains("이어폰 연결시"))
            result = "EarphoneIn";
        else if (Korean.contains("이어폰 해제시"))
            result = "EarphoneOut";
        else if (Korean.contains("배터리 N이하"))
            result = "LowBattery";
        else if (Korean.contains("배터리 N이상"))
            result = "FullBattery";
        else if (Korean.contains("양쪽 흔들기"))
            result = "SensorLR";
        else if (Korean.contains("폰뒤집기"))
            result = "UpsideDown";
        else if (Korean.contains("밝기 센서"))
            result = "SensorBright";
        else if (Korean.contains("위아래 흔들기"))
            result = "SensorUPDOWN";
        else if (Korean.contains("근접 센서"))
            result = "SensorClose";
        else if (Korean.contains("장소"))
            result = "Location";
        else if (Korean.contains("요일/시간"))
            result = "Time";

        return result;
    }

    public String Action(String English) {

        String result = English;

        if(English.contains("WifiOn"))
            result = "Wi-Fi 켜기";
        else if(English.contains("WifiOff"))
            result = "Wi-Fi 끄기";
        else if(English.contains("Sound"))
            result = "소리모드로 전환";
        else if(English.contains("Vibration"))
            result ="진동모드로 전환";
        else if(English.contains("Silence"))
            result = "무음모드로 전환";
        else if(English.contains("DataOn"))
            result = "데이터 켜기";
        else if(English.contains("DataOff"))
            result = "데이터 끄기";
        else if (English.contains("BluetoothOn"))
            result = "블루투스 켜기";
        else if (English.contains("BluetoothOff"))
            result = "블루투스 끄기";
        else if (English.contains("TellPhoneNum"))
            result = "번호읽어주기";
        else if (English.contains("TellSMS"))
            result = "문자 읽어주기";
        else if (English.contains("Camera"))
            result = "카메라";
        else if (English.contains("Flash"))
            result = "후레쉬" ;
        else if (English.contains("Bookmark"))
            result = "즐겨찾기";
        else if (English.contains("AudioRecorder"))
            result = "녹음";
        else if (English.contains("Call"))
            result ="전화걸기";
        else if (English.contains("SendingSMS"))
            result = "메세지 보내기";
        else if (English.contains("Notification"))
            result = "알림메세지";
        else if (English.contains("TextToVoice"))
            result = "음성으로바꾸기";
        else if (English.contains("VolumeRing"))
            result = "벨볼륨바꾸기" ;
        else if (English.contains("VolumeMusic"))
            result ="음악볼륨바꾸기";
        else if (English.contains("HomeScreen"))
            result ="홈화면가기";
        else if (English.contains("keyLock"))
            result = "잠금해제";
        else if (English.contains("Plantrue"))
            result ="명령 활성화";
        else if (English.contains("Planfalse"))
            result = "명령 비활성화";
        else if (English.contains("AirplaneModeOff"))
            result = "비행기모드 꺼짐";
        else if (English.contains("AirplaneModeOn"))
            result = "비행기모드 켜짐";

        return result;
    }

    public String ActionToEglish(String Korean){

        String result = Korean;

        if(Korean.contains("Wi-Fi 켜기"))
            result = "WifiOn";
        else if(Korean.contains("Wi-Fi 끄기"))
            result = "WifiOff";
        else if(Korean.contains("소리모드로 전환"))
            result = "Sound";
        else if(Korean.contains("진동모드로 전환"))
            result ="Vibration";
        else if(Korean.contains("무음모드로 전환"))
            result = "Silence";
        else if(Korean.contains("데이터 켜기"))
            result = "DataOn";
        else if(Korean.contains("데이터 끄기"))
            result = "DataOff";
        else if (Korean.contains("블루투스 켜기"))
            result = "BluetoothOn";
        else if (Korean.contains("블루투스 끄기"))
            result = "BluetoothOff";
        else if (Korean.contains("비행기모드 켜기"))
            result = "AirplaneModeOn";
        else if (Korean.contains("비행기모드 끄기"))
            result = "AirplaneModeOff";
        else if (Korean.contains("번호"))
            result = "TellPhoneNum";
        else if (Korean.contains("문자메세지"))
            result = "TellSMS";
        else if (Korean.contains("카메라"))
            result = "Camera";
        else if (Korean.contains("후레쉬"))
            result = "Flash" ;
        else if (Korean.contains("즐겨찾기"))
            result = "Bookmark";
        else if (Korean.contains("녹음"))
            result = "AudioRecorder";
        else if (Korean.contains("전화걸기"))
            result ="Call";
        else if (Korean.contains("메세지"))
            result = "SendingSMS";
        else if (Korean.contains("알림메세지"))
            result = "Notification";
        else if (Korean.contains("음성"))
            result = "TextToVoice";
        else if (Korean.contains("벨볼륨"))
            result = "VolumeRing" ;
        else if (Korean.contains("음악볼륨"))
            result ="VolumeMusic";
        else if (Korean.contains("바탕화면가기"))
            result ="HomeScreen";
        else if (Korean.contains("잠금해제"))
            result = "keyLock";
        else if (Korean.contains("명령 활성화"))
            result ="Plantrue";
        else if (Korean.contains("명령 비활성화"))
            result = "Planflase";

        return result;
    }

}