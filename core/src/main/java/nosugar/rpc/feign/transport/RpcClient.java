package nosugar.rpc.feign.transport;

import nosugar.rpc.feign.entity.FeignRequest;

import java.lang.annotation.Annotation;

/**
 * 调用请求接口
 */
public interface RpcClient {
    /**
     * 发送请求
     * @param args
     * @param feignRequest
     * @return
     */
    Object sendRequest(Object[] args,
                       FeignRequest feignRequest);
}
