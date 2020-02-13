package context.cloudstream;

import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public class MyInterfaceRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attrs = AnnotatedElementUtils.getMergedAnnotationAttributes(
                ClassUtils.resolveClassName(metadata.getClassName(), null),
                EnableBinding.class);

        EnableBinding enableBinding = AnnotationUtils.synthesizeAnnotation(attrs,
                EnableBinding.class, ClassUtils.resolveClassName(metadata.getClassName(), null));
        for (Class<?> clazz : enableBinding.value()) {
            registerBindingTargetBeanDefinitions(clazz, registry);
        }
    }

    public static void registerBindingTargetBeanDefinitions(final Class<?> type,
                                                            final BeanDefinitionRegistry registry) {
        // 注册FactoryBean
        RootBeanDefinition rootBeanDefinition1 = new RootBeanDefinition(InterfaceFactoryProxy.class);
        rootBeanDefinition1.getConstructorArgumentValues().addGenericArgumentValue(type);
        // bean的名称 type.getName(), 即全限定类目
        registry.registerBeanDefinition(type.getName(), rootBeanDefinition1);

        // 不注册也是可以，那么注册的意义是什么？1.可直接拿来用 2可以管理起来？？？
        ReflectionUtils.doWithMethods(type, new ReflectionUtils.MethodCallback() {
            @Override
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                Output output = AnnotationUtils.findAnnotation(method, Output.class);
                if (output != null) {
                    String name = getBindingTargetName(output, method);

                    // 新技能, 接口也可以用来创建bean, 前提是factoryBean 要在容器中
                    // 注册: 这个bean定义没有beanClass, 也不需要class
                    RootBeanDefinition rootBeanDefinition =  new RootBeanDefinition();
                    rootBeanDefinition.setFactoryBeanName(type.getName());
                    rootBeanDefinition.setUniqueFactoryMethodName(method.getName());
//                    rootBeanDefinition.addQualifier(new AutowireCandidateQualifier(Input.class, input.value()));
                    registry.registerBeanDefinition(name, rootBeanDefinition);
                }
            }
        });
    }

    private static String getBindingTargetName(Annotation annotation, Method method) {
        Map<String, Object> attrs = AnnotationUtils.getAnnotationAttributes(annotation, false);
        if (attrs.containsKey("value") && StringUtils.hasText((CharSequence) attrs.get("value"))) {
            return (String) attrs.get("value");
        }
        return method.getName();
    }

}
