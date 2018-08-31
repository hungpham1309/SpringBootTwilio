package com.springbootrestful.controller;

import com.springbootrestful.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/call")
public class CallController {

    private CallService callService;

    @Autowired
    public CallController(CallService callService) {
        this.callService = callService;
    }

    @Value("${PHONE_NUMBER_TO}")
    private String mobilePhone;

    @PostMapping()
    public ResponseEntity<Object> callMobile(@RequestBody Map<String, Object> payload) {
        try {
            String result = callService.call(payload.get("phone").toString());
            return new ResponseEntity<>("Call sent", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Call failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("receive")
    public ResponseEntity<Object> receiveCall() {
        callService.receiveCall();
        return new ResponseEntity<>("Call received", HttpStatus.OK);
    }

    @RequestMapping("cancel")
    public String hangupPhone() {
        callService.killCall();
        return "call";
    }
}
