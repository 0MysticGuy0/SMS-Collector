package com.vlat.MessageCollectorServer.interfaces;

import com.vlat.MessageCollectorServer.models.SMS;
import com.vlat.MessageCollectorServer.models.SMSFromServerData;

import java.util.List;

public interface SMSService {
    void save(SMS sms);
    List<SMS> findAllSMS();
    List<String> findAllPhoneNumbers();
    List<SMS> findAllSMSByReceiver(String receiverPhoneNumber);
    void clearBase();
}
