package com.example.zhifu.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pay")
public class IndexController {

    @GetMapping("/index")
    public String index1(){
        return "Index";
    }
    @GetMapping("/ok")
    public String ok(){
        return "ok";
    }

}
