package nosugar.feign.test.controller;


import nosugar.feign.test.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TestController {
    @Autowired
    FeignService service;
    @GetMapping("/test")
    public String test(String s){
        System.out.println(service.test());

        return service.test();
    }

    @PostMapping("/test2")
    public String test2(@RequestBody Map<String,String> map){
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            System.out.println(stringStringEntry.getKey());
            System.out.println(stringStringEntry.getValue());
        }
        return "aaa";
    }
}
