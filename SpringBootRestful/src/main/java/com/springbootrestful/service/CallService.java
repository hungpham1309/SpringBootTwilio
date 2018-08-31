package com.springbootrestful.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Leave;
import com.twilio.twiml.voice.Say;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class CallService {
    //Account Sid and Auth Token
    private static Logger logger = LoggerFactory.getLogger(CallService.class);

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

    public boolean checkValidPhone(String phone) {
        try {
            PhoneNumber toNumber = new PhoneNumber(phone);
            com.twilio.rest.lookups.v1.PhoneNumber checkPhoneNumber = com.twilio.rest.lookups.v1.PhoneNumber.fetcher(
                    toNumber)
                    .fetch();
            logger.info(checkPhoneNumber.getNationalFormat());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String call(String to) {
        try {
            Twilio.init(sid, auth);
            boolean checkPhone = checkValidPhone(to);
            if (!checkPhone) {
                return "Can't make call";
            }
            PhoneNumber toNumber = new PhoneNumber(to);
            PhoneNumber fromNumber = new PhoneNumber(phoneNumber);
            Call call = Call.creator(toNumber, fromNumber,
                    new URI(uriPath)).create();
            logger.info(call.getSid());
            return "Call successful";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "Can't make call";
        }

    }

    public void receiveCall() {
        Say say = new Say.Builder(
                helloMessage)
                .build();
        VoiceResponse voiceResponse = new VoiceResponse.Builder()
                .say(say)
                .build();
        logger.info("Call received: {}", voiceResponse);
    }

    public void killCall() {
        Leave leave = new Leave.Builder().build();
        VoiceResponse response = new VoiceResponse.Builder().leave(leave)
                .build();
        logger.info("Call killed: {}", response);
    }

}
