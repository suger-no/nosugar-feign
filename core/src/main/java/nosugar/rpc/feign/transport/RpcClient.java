package nosugar.rpc.feign.transport;

import nosugar.rpc.feign.entity.FeignRequest;

import java.lang.annotation.Annotation;

/**
 * 调用请求接口
 */
public interface RpcClient {
    Object sendRequest(Object[] args,
                       FeignRequest feignRequest);
}
