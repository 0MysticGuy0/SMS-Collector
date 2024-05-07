package com.mygy.messagecollector.utility;

import android.os.AsyncTask;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mygy.messagecollector.models.SMSData;
import com.mygy.messagecollector.models.UserData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ServerAPIHelper {
    private final String SERVER_ADDR = "http://192.168.43.86:8080/api/v1/sms";
    private final String USER_AGENT = "SMSCollector";

    public void sendUserData(UserData userData, APIrequestResponce responce){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("-=-=-=-=-==-=-=--=-==-");
                    URL obj = new URL(SERVER_ADDR + "/save_sms");
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("User-Agent", USER_AGENT);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setDoOutput(true);

                    con.connect();

                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    String json = ow.writeValueAsString(userData);

                    System.out.println("Sending:\n"+json);

                    try(OutputStream os = con.getOutputStream()) {
                        byte[] input = json.getBytes("utf-8");
                        os.write(input, 0, input.length);
                        os.flush();

                        int responseCode = con.getResponseCode();
                        System.out.println("RESPONCE: "+responseCode);
                        responce.responced(true);
                    }catch (Exception ex){
                        ex.printStackTrace();
                        responce.responced(false);
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                    responce.responced(false);
                }
            }
            });
    }

    public void sendMessageFromServer(String receiverPhone, String message, APIrequestResponce responce){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("-=-=-=-=-==-=-=--=-==-");
                    URL obj = new URL(SERVER_ADDR + "/send_sms?phoneNumber="+receiverPhone+
                            "&message="+message);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("POST");
                    //con.setRequestProperty("User-Agent", USER_AGENT);
                    con.setDoOutput(true);

                    con.connect();
                    System.out.println("10101010101");

                    try{
                        int responseCode = con.getResponseCode();
                        System.out.println("RESPONCE: "+responseCode);
                        responce.responced(true);
                    }catch (Exception ex){
                        ex.printStackTrace();
                        responce.responced(false);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    responce.responced(false);
                }
            }
        });
    }

    public void getServerMessages(APIrequestResponce responce){//для получения смс от сервера
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("-=-=-=-=-==-=-=--=-==-");
                    URL obj = new URL(SERVER_ADDR + "/get_sms");
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    //con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", USER_AGENT);
                    int responseCode = con.getResponseCode();
                    System.out.println("GET sms Response Code :: " + responseCode);
                    if (responseCode == HttpURLConnection.HTTP_OK) { // success
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                            System.out.println(inputLine);
                        }
                        in.close();

                        ObjectMapper objectMapper = new ObjectMapper();
                        SMSData[] data = objectMapper.readValue(response.toString(), SMSData[].class);
                        for(SMSData sd:data){
                            InMemoryStorage.addSMSData(sd);
                            //System.out.println(">"+sd.getMessage());
                        }

                        responce.responced(true);
                    } else {
                        System.out.println("GET sms request did not work.");
                        responce.responced(false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    responce.responced(false);
                }
            }
        });

    }


    public interface APIrequestResponce {
        void responced(boolean success);
    }
}
