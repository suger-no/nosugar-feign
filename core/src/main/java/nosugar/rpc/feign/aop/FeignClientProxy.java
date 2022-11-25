package nosugar.rpc.feign.aop;

import nosugar.rpc.feign.annotation.FeignClient;
import nosugar.rpc.feign.entity.FeignRequest;
import nosugar.rpc.feign.entity.ServiceInfoCache;
import nosugar.rpc.feign.transport.RpcClient;
import nosugar.rpc.feign.utils.MethodUtil;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 代理 feignClient 接口
 */
public class FeignClientProxy implements InvocationHandler {
    private String serviceName;
    private RpcClient rpcClient;
    private Map<Method, ServiceInfoCache> cache = new ConcurrentHashMap<>();

    public static  <T> T getProxy(Class<T> clazz, RpcClient rpcClient){
        FeignClient annotation = clazz.getAnnotation(FeignClient.class);
        String serviceName = annotation.value();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new FeignClientProxy(serviceName,rpcClient));
    }

    private FeignClientProxy(String serviceName,RpcClient rpcClient){
        this.serviceName = serviceName;
        this.rpcClient = rpcClient;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String methodName = method.getName();
        //equals,hashCode,toString处理
        if("equals".equals(methodName)){
            try {
                Object otherHandler = args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;
                return this.equals(otherHandler);
            } catch (IllegalArgumentException var5) {
                return false;
            }
        }else if("hashCode".equals(methodName)){
            return this.hashCode();
        }else if("toString".equals(methodName)){
            return this.toString();
        }else{
            ServiceInfoCache serviceInfoCache = cache.get(method);
            FeignRequest feignRequest = new FeignRequest();
            if(serviceInfoCache!=null){
                feignRequest.setPath(serviceInfoCache.getPath());
                feignRequest.setMethod(serviceInfoCache.getMethod());
            }
//            FeignRequest feignRequest = MethodUtil.getRealUrl(serviceName, method.getAnnotations());

            feignRequest.setReturnType(method.getReturnType());
            Object result = rpcClient.sendRequest(args, feignRequest);
            return result;
        }

    }
}
