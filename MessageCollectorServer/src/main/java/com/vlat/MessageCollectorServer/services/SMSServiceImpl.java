package com.vlat.MessageCollectorServer.services;

import com.vlat.MessageCollectorServer.interfaces.SMSDataService;
import com.vlat.MessageCollectorServer.interfaces.SMSService;
import com.vlat.MessageCollectorServer.interfaces.repositories.SMSDataRepository;
import com.vlat.MessageCollectorServer.interfaces.repositories.SMSRepository;
import com.vlat.MessageCollectorServer.models.SMS;
import com.vlat.MessageCollectorServer.models.SMSFromServerData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SMSServiceImpl implements SMSService {
    @Autowired
    private SMSRepository repository;

    @Override
    public void save(SMS sms) {
        repository.save(sms);
    }

    @Override
    public List<SMS> findAllSMS() {
        return repository.findAll();
    }

    @Override
    public List<String> findAllPhoneNumbers() {
        List<SMS> sms = findAllSMS();
        List<String> numbers = new ArrayList<>();
        for(SMS s:sms){
            String n = s.getReceiverPhoneNumber();
            if(!numbers.contains(n)){
                numbers.add(n);
            }
        }
        return numbers;
    }

    @Override
    public List<SMS> findAllSMSByReceiver(String receiverPhoneNumber) {
        StringBuilder sb = new StringBuilder(receiverPhoneNumber);
        sb.setCharAt(0,'+');
        final String NUMBER = sb.toString();

        System.out.println("\nget by receiver. All sms: "+findAllSMS().size());
        System.out.println(findAllSMS().get(0).getReceiverPhoneNumber()+" ??? "+receiverPhoneNumber);
        return findAllSMS().stream()
                .filter(sms -> sms.getReceiverPhoneNumber().equals(NUMBER))
                .collect(Collectors.toList());
    }


    @Override
    public void clearBase() {
        // repository.deleteAll();
    }
}
