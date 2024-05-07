package com.vlat.MessageCollectorServer.interfaces;

import com.vlat.MessageCollectorServer.models.SMSFromServerData;

import java.util.List;

public interface SMSDataService {
    void save(SMSFromServerData smsFromServerData);
    List<SMSFromServerData> findAllSMSData();
    void clearBase();
}
