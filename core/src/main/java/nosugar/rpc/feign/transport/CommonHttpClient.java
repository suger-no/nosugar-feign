package nosugar.rpc.feign.transport;

import nosugar.rpc.feign.exception.HttpException;
import nosugar.rpc.feign.utils.HttpUtil;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

public class CommonHttpClient implements HttpClient{
    @Override
    public byte[] send(Map<String,Object> map, RequestMethod method, String url) {

        return new byte[0];
    }

    private Request.Builder getBuilderByMethodType(RequestMethod method,Map<String,Object> map){
        Request.Builder builder = new Request.Builder();
        if(method == RequestMethod.GET){

        }else {

        }
        return null;
    }
}
