package com.egartech.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleRest {

    @GetMapping("/sample")
    public String test() {
        return "Hello World";
    }
}
