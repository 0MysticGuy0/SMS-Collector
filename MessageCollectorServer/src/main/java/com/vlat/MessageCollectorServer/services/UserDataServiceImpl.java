package com.vlat.MessageCollectorServer.services;

import com.vlat.MessageCollectorServer.interfaces.UserDataService;
import com.vlat.MessageCollectorServer.interfaces.repositories.UserDataRepository;
import com.vlat.MessageCollectorServer.models.UserData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDataServiceImpl implements UserDataService {
    @Autowired
    private UserDataRepository repository;
    @Override
    public void save(UserData userData) {
        repository.save(userData);
    }

    @Override
    public UserData getData(String phoneNumber) {
        return repository.getById(phoneNumber);
    }

    @Override
    public List<UserData> findAllUsersData() {
        return repository.findAll();
    }
}
