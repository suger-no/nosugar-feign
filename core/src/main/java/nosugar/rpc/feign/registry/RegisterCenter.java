package nosugar.rpc.feign.registry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nosugar.rpc.feign.loadbalancer.LoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;


/**
 * 注册中心
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCenter {
    DiscoveryClient discoveryClient;

    LoadBalancer loadBalancer;

    /**
     * 获取实例信息
     * @param serviceId
     * @return
     */
    public ServiceInstance getInstance(String serviceId){
        return loadBalancer.select(discoveryClient.getInstances(serviceId));
    }
}
