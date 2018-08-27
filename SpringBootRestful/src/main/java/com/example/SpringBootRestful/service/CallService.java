package com.example.SpringBootRestful.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Leave;
import com.twilio.twiml.voice.Say;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class CallService {
    //Account Sid and Auth Token

    @Value("${ACCOUNT_SID}")
    private String sid;

    @Value("${URI_PATH}")
    private String uriPath;

    @Value("${HELLO_MESSAGE}")
    private String helloMessage;

    @Value("${AUTH_TOKEN}")
    private String auth;

    @Value("${PHONE_NUMBER_FROM}")
    private String phoneNumber;

    public void call(String to) {
        try {
            Twilio.init(sid, auth);
            PhoneNumber toNumber = new PhoneNumber(to);
            PhoneNumber fromNumber = new PhoneNumber(phoneNumber);
            Call call = Call.creator(toNumber, fromNumber,
                    new URI(uriPath)).create();

            System.out.println(call.getSid());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String receiveCall() {
        Say say = new Say.Builder(
                helloMessage)
                .build();
        VoiceResponse voiceResponse = new VoiceResponse.Builder()
                .say(say)
                .build();
        System.out.println(voiceResponse.toString());
        return voiceResponse.toXml();
    }

    public void killCall() {
        Leave leave = new Leave.Builder().build();
        VoiceResponse response = new VoiceResponse.Builder().leave(leave)
                .build();

    }

}
