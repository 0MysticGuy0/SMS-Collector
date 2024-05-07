package com.mygy.messagecollector.utility;

import com.mygy.messagecollector.models.SMSData;

import java.util.ArrayList;
import java.util.List;

public abstract class InMemoryStorage {
    private static List<SMSData> smsDatas = new ArrayList<>();

    public static List<SMSData> getSmsDatas() {
        return smsDatas;
    }

    public static void addSMSData(SMSData smsData) {
        smsDatas.add(smsData);
    }
}
