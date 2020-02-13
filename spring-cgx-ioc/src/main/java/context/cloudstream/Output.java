package context.cloudstream;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD,
        ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Output {

	String value() default "";

}