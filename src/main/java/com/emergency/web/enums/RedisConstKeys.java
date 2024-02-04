package com.emergency.web.enums;

import com.alibaba.fastjson.JSONObject;

public class RedisConstKeys {
    // consumer
    public static final String EMERGENCY_KEY = "EMERGENCY_KEY";
    public static final String EMERGENCY_KEY2 = "EMERGENCY_KEY2";

    public static final String START_CALL = "START_CALL";
    public static final String CANCEL_CALL = "CANCEL_CALL";
    public static final String ACCEPT = "ACCEPT";
    public static final String CANCEL = "CANCEL";
    public static final String ARRIVED = "ARRIVED";
    public static final String FINISH = "FINISH";//
    public static final String VOLUNTEER_GET_AED = "VOLUNTEER_GET_AED";
    public static final String VOLUNTEER_NO_AED = "VOLUNTEER_NO_AED";
    public static final String PATIENT_EXTRA = "PATIENT_EXTRA";
    public static final String ABANDON_RESCUE = "ABANDON_RESCUE";
    public static final String rescueeUIDConstKey = "rescueeUID";
    public static final Integer AED_STANDARD_DISTANCE = 5000;
    public static final Integer SMS_STANDARD_DISTANCE = 500;
    public static final String ONLINE_USER_KEY = "online_user*";
    public static final String USER_IS_BUSY_KEY = "userIsBusy*";
    public static final String VOLUNTEER_DISPATCH_LIST = "VOLUNTEER_DISPATCH_LIST";
    public static final String RESCUER_DISPATCH_LIST = "RESCUER_DISPATCH_LIST";
    public static final String AED_VOLUNTEER_CHOICE_AID = "AED_VOLUNTEER_CHOICE_AID:";
    public static final String CANCEL_RESCUE_AID = "CANCEL_RESCUE_AID:";
    public static final String SEND_MSG = "SEND_MSG:";
    public static final String EMERGENCY_SEND_SMS = "EMERGENCY_SEND_SMS";
    public static final String RESCUE_PEOPLE = "rescue_people:";

    public static String wrapperDispatchMessage(String aid, String callerLat, String callerLng, String rescueeUIDStr) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aid", aid);
        jsonObject.put("callerLat", callerLat);
        jsonObject.put("callerLng", callerLng);
        jsonObject.put("rescueeUIDStr", rescueeUIDStr);
        return jsonObject.toJSONString();
    }


}

