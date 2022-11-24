package nosugar.rpc.feign.loadbalancer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;



public class RandomLoadBalancer implements LoadBalancer{
    @Override
    public ServiceInstance select(List<ServiceInstance> list) {
        return list.get(new Random().nextInt(list.size()));
    }
}
