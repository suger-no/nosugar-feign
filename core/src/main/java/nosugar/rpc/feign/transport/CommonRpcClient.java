package nosugar.rpc.feign.transport;

import com.alibaba.fastjson.JSON;
import nosugar.rpc.feign.entity.FeignRequest;
import nosugar.rpc.feign.registry.RegisterCenter;
import nosugar.rpc.feign.serializer.CommonSerializer;
import nosugar.rpc.feign.utils.HttpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;
import java.util.Map;

/**
 * 默认 feign  client
 */
public class CommonRpcClient implements RpcClient{
    private CommonSerializer serializer;
    private RegisterCenter registerCenter;
    public CommonRpcClient(CommonSerializer serializer,RegisterCenter registerCenter){
        this.registerCenter=registerCenter;
        this.serializer = serializer;
    }
    @Override
    public Object sendRequest(Object[] args,
                              FeignRequest feignRequest){
        Map<String, String> param = serializer.serialize(args);
        feignRequest.setParam(param);
        byte[] bytes = HttpUtil.request(feignRequest);
        return serializer.deserialize(bytes, feignRequest.getReturnType());
    }

//    private RequestMethod parseAnnotations(Annotation[] annotations){
//        for (Annotation annotation : annotations) {
//            if(annotation instanceof RequestMapping){
//                RequestMethod[] method = ((RequestMapping) annotation).method();
//                return method[0];
//            }else if (!isMetaAnnotation(annotation)){
//                Annotation[] annotations1 = annotation.annotationType().getAnnotations();
//                RequestMethod requestMethod = parseAnnotations(annotations1);
//                if(requestMethod!=null){
//                    return requestMethod;
//                }
//            }
//        }
//        return null;
//    }
//
//    private boolean isMetaAnnotation(Annotation annotation){
//        if(annotation instanceof Target ||
//           annotation instanceof Retention ||
//            annotation instanceof Documented ||
//            annotation instanceof Inherited){
//            return true;
//        }
//        return false;
//    }




}
