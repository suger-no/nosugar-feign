package nosugar.rpc.feign;

import nosugar.rpc.feign.annotation.EnableFeign;
import nosugar.rpc.feign.annotation.FeignClient;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FeignClientRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
    private ResourceLoader resourceLoader;
    private Environment environment;
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        registerFeignClients(metadata,registry);
    }

    private void registerFeignClients(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setEnvironment(this.environment);
        scanner.addIncludeFilter(new AnnotationTypeFilter(FeignClient.class));
        //feign clients路径包名
        Set<String> basePackages;
        Map<String, Object> attrs = metadata.getAnnotationAttributes(EnableFeign.class.getCanonicalName());
        basePackages = getBasePackages(metadata);

        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
            for (BeanDefinition candidateComponent : candidateComponents) {
                if (candidateComponent instanceof AnnotatedBeanDefinition){
                    AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                    AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                    Assert.isTrue(annotationMetadata.isInterface(),"@FeignClient 只能被用于接口上");

                    Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(FeignClient.class.getCanonicalName());
                    
                    String name = getClientName(annotationAttributes);
//                    registerClientConfiguration(registry, name,
//                            annotationAttributes.get("configuration"));

                    registerFeignClient(registry, annotationMetadata, annotationAttributes);
                }
            }
        }
    }

    private void registerFeignClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata, Map<String, Object> attributes) {
        String className = annotationMetadata.getClassName();
        BeanDefinitionBuilder definition = BeanDefinitionBuilder
                .genericBeanDefinition(FeignClientFactoryBean.class);
//        validate(attributes);
//        definition.addPropertyValue("url", getUrl(attributes));
//        definition.addPropertyValue("path", getPath(attributes));
        String name = (String) attributes.get("value");
        definition.addPropertyValue("name", name);
//        String contextId = getContextId(attributes);
//        definition.addPropertyValue("contextId", contextId);
        definition.addPropertyValue("type", className);
        definition.addPropertyValue("decode404", attributes.get("decode404"));
        definition.addPropertyValue("fallback", attributes.get("fallback"));
        definition.addPropertyValue("fallbackFactory", attributes.get("fallbackFactory"));
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

        String alias = attributes.get("value") + "FeignClient";
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();

//        boolean primary = (Boolean) attributes.get("primary"); // has a default, won't be
        // null

//        beanDefinition.setPrimary(primary);

//        String qualifier = getQualifier(attributes);
//        if (StringUtils.hasText(qualifier)) {
//            alias = qualifier;
//        }

        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className,
                new String[] { alias });
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }


    private void registerClientConfiguration(BeanDefinitionRegistry registry, String name, Object configuration) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder
                .genericBeanDefinition(FeignClientSpecification.class);
        builder.addConstructorArgValue(name);
        builder.addConstructorArgValue(configuration);
        registry.registerBeanDefinition(
                name + "." + FeignClientSpecification.class.getSimpleName(),
                builder.getBeanDefinition());
    }

    private String getClientName(Map<String, Object> client) {
        if(client == null){
            return null;
        }
        String value = (String) client.get("value");
        return value;
    }

    private Set<String> getBasePackages(AnnotationMetadata metadata) {

        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(EnableFeign.class.getCanonicalName());
        Set<String> basePackages = new HashSet<>();
        for (String pkg : (String[]) annotationAttributes.get("basePackages")) {
            if(StringUtils.hasText(pkg)){
                basePackages.add(pkg);
            }
        }


        return basePackages;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    protected ClassPathScanningCandidateComponentProvider getScanner(){
        return new ClassPathScanningCandidateComponentProvider(false,this.environment){
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if(beanDefinition.getMetadata().isIndependent()){
                    if(!beanDefinition.getMetadata().isAnnotation()){
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
