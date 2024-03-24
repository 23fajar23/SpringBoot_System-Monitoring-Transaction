package com.monitor.transaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TesterController {

    @GetMapping("/admin")
    public String hiAdmin(){
        return "Hi Admin";
    }

    @GetMapping("/customer")
    public String hiCustomer(){
        return "Hi Customer";
    }


}
