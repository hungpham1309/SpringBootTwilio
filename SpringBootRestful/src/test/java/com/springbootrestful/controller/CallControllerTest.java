package com.springbootrestful.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springbootrestful.service.CallService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CallController.class)
public class CallControllerTest {
    @Autowired
    private MockMvc mvc;

    private static final String jsonMobile ="{" +
            "\"phone\":\"+84936210777\"" +
            "}";

    private static final String jsonFakeMobile ="{" +
            "\"phone\":\"+8493\"" +
            "}";
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CallService service;

    @Before
    public void setUp() throws Exception {
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void shouldCallSuccessful_WhenPhoneIsValid() throws Exception {

        mvc.perform(post("/call")
                .content(jsonMobile)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCallFail_WhenPhoneIsInvalid() throws Exception {
        mvc.perform(post("/call")
                .content(jsonFakeMobile)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}