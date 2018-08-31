package com.springbootrestful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "index";
    }


    @RequestMapping("call-phone")
    public String callPage(){
        return "call";
    }
}
