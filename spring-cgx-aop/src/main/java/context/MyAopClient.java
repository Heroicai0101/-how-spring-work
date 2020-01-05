package context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author guoxiong
 * 2019/12/21 下午9:19
 */
@EnableAspectJAutoProxy
@ComponentScan
public class MyAopClient {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyAopClient.class);

        MyCalculator calculator = context.getBean(MyCalculator.class);
        calculator.div(100, 10);
//        calculator.div(100, 0);
    }

}
