package com.vlat.MessageCollectorServer.controllers;

import com.vlat.MessageCollectorServer.interfaces.SMSDataService;
import com.vlat.MessageCollectorServer.interfaces.SMSService;
import com.vlat.MessageCollectorServer.interfaces.UserDataService;
import com.vlat.MessageCollectorServer.models.SMS;
import com.vlat.MessageCollectorServer.models.SMSFromServerData;
import com.vlat.MessageCollectorServer.models.UserData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/sms")
@AllArgsConstructor
public class MainRestController {
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private SMSDataService smsDataService;
    @Autowired
    private SMSService smsService;
    @PostMapping("/save_sms")
    public void saveSMSData(@RequestBody UserData userData){
        System.out.println("\n\n\n=-=-=-=-=-=-=-=-=-==\n"+userData.getPhoneNumber()+"\n\n\n=-=-=-=-=-=-=-=-=-==\n");
        //userDataService.save(userData);

        String[] messages = userData.getMessages().split(">-=-=-=-=-<");
        for(String msg:messages){
            String[] data = msg.split(":");
            smsService.save(    new SMS(data[0],userData.getPhoneNumber(),data[1])  );
        }
    }
    @GetMapping("/get_sms")
    public List<SMSFromServerData> getSMSFromServerData(){
        List<SMSFromServerData> res = smsDataService.findAllSMSData();
        smsDataService.clearBase();
        return res;
    }
    @GetMapping("/")
    public String apiHello(){
        return "hello from API";
    }

    @PostMapping("/send_sms")
    public void sendSMS(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("message") String message){
        System.out.println("-=-=-Message to\n"+phoneNumber+":\n\t"+message+"\n-=-=-");
        smsDataService.save(new SMSFromServerData(phoneNumber,message));
    }

    @GetMapping("/get_all_sms")
    public List<SMS> getAllSMS(){
        List<SMS> smsList = smsService.findAllSMS();
        return smsList;
    }

    @GetMapping("/get_all_phones")
    public List<String> getAllPhones(){
        List<String> numbers = smsService.findAllPhoneNumbers();
        return numbers;
    }

    @GetMapping("/get_sms_by_receiver")
    public List<SMS> getSMSByReceiver(@RequestParam("phoneNumber") String phoneNumber){
        List<SMS> smsList = smsService.findAllSMSByReceiver(phoneNumber);
        System.out.println("1010101010110101 "+smsList.size());
        return smsList;
    }
}
