package com.vlat.MessageCollectorServer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="sms")
@AllArgsConstructor
@NoArgsConstructor
public class SMS {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String senderPhoneNumber;
    private String receiverPhoneNumber;
    private String message;

    public SMS(String senderPhoneNumber, String receiverPhoneNumber, String message) {
        this.senderPhoneNumber = senderPhoneNumber;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.message = message;
    }
}
