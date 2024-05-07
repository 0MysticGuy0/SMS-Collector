package com.vlat.MessageCollectorServer.interfaces.repositories;

import com.vlat.MessageCollectorServer.models.SMS;
import com.vlat.MessageCollectorServer.models.SMSFromServerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SMSRepository extends JpaRepository<SMS,Long> {
    List<SMS> findAll();
}
