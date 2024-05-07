package com.mygy.messagecollector.utility;


public class SimpleCodeGenerator implements CodeGenerator {
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final int length;

    public SimpleCodeGenerator(){
        this.length = 6;
    }


    @Override
    public String generate() {
        StringBuilder passwd = new StringBuilder();
        for(int i = 0; i<length;i++){
            passwd.append(
                    alphabet.charAt((int)(Math.random()*(alphabet.length()-1))));
        }
        return passwd.toString();
    }
}
