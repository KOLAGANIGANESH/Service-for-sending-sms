package com.ganesh.Springbootsmsdemo;

import com.example.springsmstrigger.model.Sms;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.messageresolver.IMessageResolver;

@Service
@Slf4j
public class SmsService {

   @value("#{systemEnvironment['TWILIO_ACCOUNT_SID']}")
   private string SID;

   @value("#{systemEnvironment['TWILIO_AUTH_TOKEN']}")
   private String authToken;

    @value("#{systemEnvironment['TWILIO_PHONE_NUMBER]}")
    private String fromPhoneNumber;

    @value("#{systemEnvironment['TO_PHONE_NUMBER]}")
    private String toPhoneNumber;

    public void send(SmsPojo sms) {
        Twilio.init(SID, authToken);

        Message.message = Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(fromPhoneNumber), sms.getMessage()).create();
        log.info("Sms triggered Successfully and the sid:",message.getSid);// Unique resource ID created to manage this transaction

    }

}
