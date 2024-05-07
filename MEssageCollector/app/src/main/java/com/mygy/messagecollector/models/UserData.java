package com.mygy.messagecollector.models;

public class UserData {
    private String phoneNumber;
    private String messages;

    public UserData() {
    }

    public UserData(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessages() {
        return messages;
    }
}
