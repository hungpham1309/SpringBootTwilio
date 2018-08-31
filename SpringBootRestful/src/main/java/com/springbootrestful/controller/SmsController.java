package com.springbootrestful.controller;

import com.springbootrestful.entity.Sms;
import com.springbootrestful.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SmsController {
    private SmsService smsService;

    @Autowired
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @Value("${PHONE_NUMBER_TO}")
    private String mobilePhone;

    @PostMapping(value = "send")
    public ResponseEntity<Object> sendSMS(@RequestBody Sms sms){
        try {
            smsService.sendMessage(sms.getPhone(),sms.getMessage());
            return new ResponseEntity<>("Message sent", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Message sent", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "receive")
    public ResponseEntity<Object> receiveAndSendSMS(@RequestParam(value = "Body") String message){
        smsService.sendMessage(mobilePhone, "You just sent: " + message);
        return new ResponseEntity<>("Message received", HttpStatus.OK);
    }

}
