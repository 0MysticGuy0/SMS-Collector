package com.vlat.MessageCollectorServer.services;

import com.vlat.MessageCollectorServer.interfaces.SMSDataService;
import com.vlat.MessageCollectorServer.interfaces.repositories.SMSDataRepository;
import com.vlat.MessageCollectorServer.models.SMSFromServerData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SMSFromServerDataServiceImpl implements SMSDataService {
    @Autowired
    private SMSDataRepository repository;

    @Override
    public void save(SMSFromServerData smsFromServerData) {
        repository.save(smsFromServerData);
    }

    @Override
    public List<SMSFromServerData> findAllSMSData() {
        return repository.findAll();
    }

    @Override
    public void clearBase() {
        repository.deleteAll();
    }
}
