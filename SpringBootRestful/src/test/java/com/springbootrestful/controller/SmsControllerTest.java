package com.springbootrestful.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootrestful.entity.Sms;
import com.springbootrestful.service.SmsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SmsController.class)
public class SmsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SmsService service;

    private JacksonTester<Sms> jsonTester;
    private Sms sms;

    @Before
    public void setup() {
        sms = new Sms();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void testSendMessageSuccessful() throws Exception {
        sms.setMessage("Test message controller");
        sms.setPhone("+84936210777");
        String smsJson = jsonTester.write(sms).getJson();

        mvc.perform(post("/sms/send")
                .content(smsJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testSendMessageFailWithPhoneInvalid() throws Exception {
        sms.setMessage("Test message controller");
        sms.setPhone("+84936");
        String smsJson = jsonTester.write(sms).getJson();

        mvc.perform(post("/sms/send")
                .content(smsJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());

    }

}