package nosugar.rpc.feign.annotation;

import nosugar.rpc.feign.FeignClientRegistrar;
import nosugar.rpc.feign.autoconfigure.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({FeignClientRegistrar.class, FeignAutoConfiguration.class})
public @interface EnableFeign {
    String[] basePackages() default {};
}
