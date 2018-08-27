package com.example.SpringBootRestful.controller;

import com.example.SpringBootRestful.service.CallService;
import com.example.SpringBootRestful.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private CallService callService;

    @Value("${PHONE_NUMBER_TO}")
    private String mobilePhone;

    @RequestMapping("/")
    public String home(){
        return "index";
    }

    @RequestMapping("send-message")
    public String sms(){
        return "message";
    }

    @RequestMapping("send")
    public String sendSMS(@RequestParam("phone") String phone, @RequestParam("message") String message){
        smsService.sendMessage(phone,message);
        return "message";
    }

    @PostMapping(value = "receive")
    public String receiveAndSendSMS(@RequestParam(value = "Body") String message){
        smsService.sendMessage(mobilePhone, "You just sent: " + message);
        return "message";
    }

    @PostMapping(value = "receiveCall")
    public String receiveCall(){
        callService.receiveCall();
        return "call";
    }

    @RequestMapping("call")
    public String callMobile(){
        callService.call(mobilePhone);
        return "call";
    }

    @RequestMapping("cancel")
    public String hangupPhone(){
        callService.killCall();
        return "call";
    }

    @RequestMapping("call-phone")
    public String callPage(){
        return "call";
    }
}
