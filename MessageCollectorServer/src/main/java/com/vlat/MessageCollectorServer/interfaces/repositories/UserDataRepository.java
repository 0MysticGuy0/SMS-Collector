package com.vlat.MessageCollectorServer.interfaces.repositories;

import com.vlat.MessageCollectorServer.models.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDataRepository extends JpaRepository<UserData,String> {
    List<UserData> findAll();
    UserData getByPhoneNumber(String phoneNumber);
}
