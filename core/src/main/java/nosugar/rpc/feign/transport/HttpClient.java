package nosugar.rpc.feign.transport;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

public interface HttpClient {
     byte[] send(Map<String,Object> map, RequestMethod method, String url);
}
