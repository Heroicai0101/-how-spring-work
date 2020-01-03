package context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author guoxiong
 * 2019/12/21 下午9:19
 */
@ComponentScan
public class MySpring {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MySpring.class);

        CityService cityService = context.getBean(CityService.class);
        cityService.hello();
    }

}
