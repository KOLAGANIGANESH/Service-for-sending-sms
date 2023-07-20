package com.ganesh.Springbootsmsdemo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SMSController {

    @Autowired
    SmsService smsservice;

    @Autowired
    SimpMessagingTemplate webSocket;

    private static final String  TOPIC_NAME = "/topic/sms";

    @RequestMapping(value = "/sms",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.
             TEXT_PLAIN_VALUE)
    public String smsSend(@RequestBody SmsPojo sms) {
        try{
            service.sendSms(sms);
        }
        catch(Exception e){

            webSocket.convertAndSend(TOPIC_NAME,  getDateAndTime()+" Error sending the SMS: ");
            throw e;
        }
        webSocket.convertAndSend(TOPIC_NAME, getDateAndTime() + " SMS has been sent!: ");
        return "sucess";
    }

    @RequestMapping(value = "/smscallback",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN)
    public void smsCallback(@RequestBody MultiValueMap<String, String> map) {
        service.recivce();
        webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": Twilio has made a callback request! Here are the contents: "+map.toString());
    }

    public String getDateAndTime() {
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }
}
