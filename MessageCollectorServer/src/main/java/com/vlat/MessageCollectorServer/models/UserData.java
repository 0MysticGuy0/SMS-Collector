package com.vlat.MessageCollectorServer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Type;

import java.util.ArrayList;

@Entity
@Data
@Table(name="user_data")
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    @Id
    @NonNull
    private String phoneNumber;
    @Column(columnDefinition = "text")
    private String messages;

}
