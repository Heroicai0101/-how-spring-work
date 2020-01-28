package context;

import context.annotation.atImport.Dog;
import context.annotation.atImport.MyImportBeanDefinitionRegistrar;
import context.annotation.atImport.MyImportSelector;
import context.ioc.CityService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author guoxiong
 * 2019/12/21 下午9:19
 */
@Import(value = {MyImportSelector.class, Dog.class, MyImportBeanDefinitionRegistrar.class})
@ComponentScan
public class MyIocClient {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyIocClient.class);

        CityService cityService = context.getBean(CityService.class);
        cityService.hello();
    }

}
