package nosugar.rpc.feign.exception;

public class HttpException extends RuntimeException{
    public HttpException(String msg){
        super(msg);
    }
}
