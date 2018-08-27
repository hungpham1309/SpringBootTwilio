package com.example.SpringBootRestful.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    //Account Sid and Auth Token at twilio.com/console

    @Value("${ACCOUNT_SID}")
    private String sid;

    @Value("${AUTH_TOKEN}")
    private String auth;

    @Value("${PHONE_NUMBER_FROM}")
    private String phoneNumber;

    public void sendMessage(String to, String message){
        Twilio.init(sid,auth);
        PhoneNumber toNumber = new PhoneNumber(to);
        PhoneNumber fromNumber = new PhoneNumber(phoneNumber);
        Message messageToSend = Message.creator(
                toNumber,
                fromNumber,
                message
        ).create();

        System.out.println("Message: " + messageToSend.getBody() );
    }

    public void getAllMessage(){
        Twilio.init(sid,auth);
        Iterable<Message> messages = Message.reader().read();
        for (Message m : messages) {
            System.out.println(m.getSid());
            System.out.println(m.getBody());
        }
    }
}
