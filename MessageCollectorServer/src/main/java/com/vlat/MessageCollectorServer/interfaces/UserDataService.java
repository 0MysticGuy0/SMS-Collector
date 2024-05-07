package com.vlat.MessageCollectorServer.interfaces;

import com.vlat.MessageCollectorServer.models.UserData;

import java.util.List;

public interface UserDataService {
    void save(UserData userData);
    UserData getData(String phoneNumber);
    List<UserData> findAllUsersData();
}
