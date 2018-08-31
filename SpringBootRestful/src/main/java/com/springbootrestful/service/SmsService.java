package com.springbootrestful.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    //Account Sid and Auth Token at twilio.com/console
    private static Logger logger = LoggerFactory.getLogger(CallService.class);

    @Value("${ACCOUNT_SID}")
    private String sid;

    @Value("${AUTH_TOKEN}")
    private String auth;

    @Value("${PHONE_NUMBER_FROM}")
    private String phoneNumber;

    public String sendMessage(String to, String message){
        try {
            Twilio.init(sid,auth);
            PhoneNumber toNumber = new PhoneNumber(to);
            PhoneNumber fromNumber = new PhoneNumber(phoneNumber);
            Message messageToSend = Message.creator(
                    toNumber,
                    fromNumber,
                    message
            ).create();

            logger.info("Message sent: {}", messageToSend.getBody());
            return messageToSend.getBody();
        } catch (Exception e){
            logger.error(e.getMessage());
            return "Sent message failed.";
        }

    }

    public void getAllMessage(){
        Twilio.init(sid,auth);
        Iterable<Message> messages = Message.reader().read();
        for (Message m : messages) {
            logger.info("Message: {}", m.getBody());
        }
    }
}
