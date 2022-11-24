package nosugar.rpc.feign.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nosugar.rpc.feign.entity.FeignRequest;
import nosugar.rpc.feign.exception.HttpException;
import nosugar.rpc.feign.registry.RegisterCenter;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.*;

/**
 * 获取方法对应的路径
 */
public class MethodUtil {
    private static RegisterCenter registerCenter;

    public static void setRegisterCenter(RegisterCenter registerCenter) {
        MethodUtil.registerCenter = registerCenter;
    }

    public static FeignRequest getRealUrl(String serviceName,Annotation[] annotations){
        ServiceInstance instance = registerCenter.getInstance(serviceName);
        FeignRequest request = getPath(annotations);
        String host = instance.getHost();
        int port = instance.getPort();
        request.setHost(host);
        request.setPort(port);
        return request;
    }


    /**
     * 获取请求类型，和路径
     * 很傻逼的方法
     * @param annotations
     * @return
     */
    private static FeignRequest getPath(Annotation[] annotations){
        FeignRequest feignRequest = new FeignRequest();

        for (Annotation annotation : annotations) {
            if(annotation instanceof GetMapping){
                feignRequest.setMethod(RequestMethod.GET);
                GetMapping annotation1 = (GetMapping) annotation;
                String name = annotation1.value()[0];
                feignRequest.setPath(name);
            }else if(annotation instanceof PostMapping){
                feignRequest.setMethod(RequestMethod.POST);
                feignRequest.setMethod(RequestMethod.GET);
                PostMapping annotation1 = (PostMapping) annotation;
                String name = annotation1.value()[0];
                feignRequest.setPath(name);
            }else if(annotation instanceof PutMapping){
                feignRequest.setMethod(RequestMethod.PUT);
                feignRequest.setMethod(RequestMethod.GET);
                PutMapping annotation1 = (PutMapping) annotation;
                String name = annotation1.value()[0];
                feignRequest.setPath(name);
            }else if (annotation instanceof DeleteMapping){
                feignRequest.setMethod(RequestMethod.DELETE);
                feignRequest.setMethod(RequestMethod.GET);
                DeleteMapping annotation1 = (DeleteMapping) annotation;
                String name = annotation1.value()[0];
                feignRequest.setPath(name);
            }else {
                throw new HttpException("该请求类型暂不支持");
            }
        }
        return feignRequest;
    }
}
