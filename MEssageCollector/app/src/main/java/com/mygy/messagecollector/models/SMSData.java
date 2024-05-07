package com.mygy.messagecollector.models;

public class SMSData {

    private Long id;
    private String phoneNumber;
    private String message;

    public SMSData(String phoneNumber, String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public SMSData(Long id, String phoneNumber, String message) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public SMSData() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
