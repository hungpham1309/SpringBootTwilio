package com.springbootrestful.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@TestPropertySource("classpath:application.properties")
@RunWith(SpringRunner.class)
public class SmsServiceTest {
    @TestConfiguration
    static class SmsServiceTestConfiguration {

        @Bean
        public SmsService smsService() {
            return new SmsService();
        }
    }

    @Value("${ACCOUNT_SID}")
    private String sid;

    @Value("${AUTH_TOKEN}")
    private String auth;

    @Value("${PHONE_NUMBER_FROM}")
    private String phoneNumber;

    @Autowired
    SmsService smsService;

    @Test
    public void testSendSmsSuccessWithPhoneValid(){
        String phoneTo = "+84936210777";
        String expected = "Sent from your Twilio trial account - Test send message success";
        String actualResult = smsService.sendMessage(phoneTo, expected);
        assertThat(actualResult, equalTo(expected));
    }

    @Test
    public void testSendSmsSuccessWithPhoneInvalid(){
        String phoneTo = "+84936210777145";
        String expected = "Sent message failed.";
        String actualResult = smsService.sendMessage(phoneTo, expected);
        assertThat(actualResult, equalTo(expected));
    }
}