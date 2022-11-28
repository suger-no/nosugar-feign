# nosugar-feign
--持续更新中


一个简单的服务调用，用于学习
可以在springboot中使用

## 使用方式
1.maven  install 到本地后
导入

````
<dependency>
            <groupId>study.nosugar</groupId>
            <artifactId>core</artifactId>
            <version>1.0.0</version>
        </dependency>
````

````
@EnableFeign(basePackages = "nosugar.feign.test.service") //要扫描的包路径
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}



@FeignClient("test2")   //实现服务调用的接口 
public interface FeignService {
    @GetMapping("/test")
    String test();
}
````


当前只能更改负载均衡方式,\
将nosugar.rpc.feign.loadbalancer.LoadBalancer实现类注入spring容器中即可

目前仅支持随机选择，


