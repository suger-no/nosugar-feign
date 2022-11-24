package nosugar.rpc.feign.registry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nosugar.rpc.feign.loadbalancer.LoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCenter {
    DiscoveryClient discoveryClient;

    LoadBalancer loadBalancer;

    public ServiceInstance getInstance(String serviceId){
        return loadBalancer.select(discoveryClient.getInstances(serviceId));
    }
}
