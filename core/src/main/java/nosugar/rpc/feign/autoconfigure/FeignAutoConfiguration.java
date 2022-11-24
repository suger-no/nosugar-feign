package nosugar.rpc.feign.autoconfigure;

import nosugar.rpc.feign.loadbalancer.LoadBalancer;
import nosugar.rpc.feign.loadbalancer.RandomLoadBalancer;
import nosugar.rpc.feign.registry.RegisterCenter;
import nosugar.rpc.feign.serializer.CommonJsonSerializer;
import nosugar.rpc.feign.serializer.CommonSerializer;
import nosugar.rpc.feign.transport.CommonRpcClient;
import nosugar.rpc.feign.transport.RpcClient;
import nosugar.rpc.feign.utils.MethodUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignAutoConfiguration {


    @Bean
    public RegisterCenter registerCenter(DiscoveryClient discoveryClient, LoadBalancer loadBalancer){
        RegisterCenter registerCenter = new RegisterCenter(discoveryClient, loadBalancer);
        MethodUtil.setRegisterCenter(registerCenter);
        return registerCenter;
    }


    @Bean
    @ConditionalOnMissingBean(LoadBalancer.class)
    public LoadBalancer loadBalancer(){
        return new RandomLoadBalancer();
    }

    @Bean
    @ConditionalOnMissingBean(CommonSerializer.class)
    public CommonSerializer commonSerializer(){
        return new CommonJsonSerializer();
    }

    @Bean
    @ConditionalOnMissingBean(RpcClient.class)
    public RpcClient commonRpcClient(CommonSerializer serializer,RegisterCenter registerCenter){
        return new CommonRpcClient(serializer,registerCenter);
    }
}
