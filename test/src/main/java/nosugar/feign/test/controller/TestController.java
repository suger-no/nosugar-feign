package nosugar.feign.test.controller;


import nosugar.feign.test.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    FeignService service;
    @GetMapping("/test")
    public String test(String s){
        List<String> test = service.test();
        for (String s1 : test) {
            System.out.println(s1);
        }
        System.out.println();

        return "ok";
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
