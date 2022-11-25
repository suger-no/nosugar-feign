package nosugar.rpc.feign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nosugar.rpc.feign.aop.FeignClientProxy;
import nosugar.rpc.feign.serializer.CommonSerializer;
import nosugar.rpc.feign.transport.RpcClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Data
public class FeignClientFactoryBean implements FactoryBean<Object>, InitializingBean, ApplicationContextAware {
    private Class<?> type;

    private String name;

    private String url;

    private String contextId;

    private String path;

    private boolean decode404;

    private ApplicationContext applicationContext;

    private Class<?> fallback = void.class;

    private Class<?> fallbackFactory = void.class;

    @Autowired
    RpcClient rpcClient;

    /**
     * 获取FeignClient代理对象
     * @return
     * @throws Exception
     */
    @Override
    public Object getObject() throws Exception {
        return FeignClientProxy.getProxy(type,applicationContext.getBean(RpcClient.class));
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
