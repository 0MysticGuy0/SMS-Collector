package com.vlat.MessageCollectorServer.interfaces.repositories;

import com.vlat.MessageCollectorServer.models.SMSFromServerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SMSDataRepository extends JpaRepository<SMSFromServerData,Long> {
    List<SMSFromServerData> findAll();
}
