package context.cloudstream;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 这个注解必须标识在能被spring管理的类上面;
 * 类似这种 @EnableXXX 注解能够生效的原因是因为存在对应的类, 比如 MyInterfaceRegistrar 里面有对 @EnableBinding 的解析逻辑
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Configuration
@Import({MyInterfaceRegistrar.class})
public @interface EnableBinding {

    /**
     * A list of interfaces having methods annotated with {@link Output} to indicate binding targets.
     */
    Class<?>[] value() default {};

}