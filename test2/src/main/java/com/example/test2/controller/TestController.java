package com.example.test2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    @GetMapping("/test")
    public List<String> test(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("awdaw");
        strings.add("awd123123aw");
        strings.add("awd312aw");
        return strings;
    }
}
