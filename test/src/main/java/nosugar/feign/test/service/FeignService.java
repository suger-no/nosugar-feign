package nosugar.feign.test.service;

import nosugar.rpc.feign.annotation.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("test2")
public interface FeignService {
    @GetMapping("/test")
    List<String> test();
}
