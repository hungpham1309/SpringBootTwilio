package com.springbootrestful.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@ContextConfiguration
public class CallServiceTest {

    @TestConfiguration
    static class CallServiceTestConfiguration {

        @Bean
        public CallService callService() {
            return new CallService();
        }
    }

    @Autowired
    CallService callService;

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

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldCallSuccessful_WhenPhoneIsValid(){
        String phoneToCall = "+84936210777";
        String expected = "Call successful";
        String actualResult = callService.call(phoneToCall);
        assertThat(actualResult, equalTo(expected));
    }

    @Test
    public void shouldCallFail_WhenPhoneIsInvalid(){
        String phoneToCall = "+8493621077711";
        String expected = "Can't make call";
        String actualResult = callService.call(phoneToCall);
        assertThat(actualResult, equalTo(expected));
    }


}