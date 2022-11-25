package nosugar.rpc.feign.entity;

import org.springframework.web.bind.annotation.RequestMethod;

public class ServiceInfoCache {
    private RequestMethod method;
    private String path;

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
