package nosugar.rpc.feign.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识服务，用于远程接口的实现
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FeignClient {
    //服务名
    String value();

    boolean decode404() default false;
    Class<?> fallback() default void.class;
    Class<?> fallbackFactory() default void.class;
    boolean primary() default true;

}
