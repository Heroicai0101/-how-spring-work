package context;

import context.annotation.atImport.Dog;
import context.annotation.atImport.MyImportBeanDefinitionRegistrar;
import context.annotation.atImport.MyImportConfigurationClass;
import context.annotation.atImport.MyImportSelector;
import context.cloudstream.EnableBinding;
import context.cloudstream.MessageChannel;
import context.cloudstream.XInterface;
import context.cloudstream.YInterface;
import context.ioc.CityService;
import context.ioc.autowireMode.MyService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author guoxiong
 * 2019/12/21 下午9:19
 */
@Import(value = {MyImportSelector.class, Dog.class, MyImportBeanDefinitionRegistrar.class, MyImportConfigurationClass.class})
@ComponentScan
@EnableBinding(value = {XInterface.class, YInterface.class})
public class MyIocClient {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyIocClient.class);

        CityService cityService = context.getBean(CityService.class);
        cityService.hello();

        /* ---------- 模拟 CloudStream 使用方式---------- */
        XInterface xInterface = context.getBean(XInterface.class);
        xInterface.output().send("Hello");

        MessageChannel messageChannel = context.getBean("myOutputX", MessageChannel.class);
        messageChannel.send("World");

        /* ---------- 测试注入模型 ---------- */
        MyService myService = context.getBean("myService", MyService.class);
        System.out.println(myService);

        boolean myImportConfigurationClass = context.containsBean("myImportConfigurationClass");
        System.out.println(myImportConfigurationClass);

        // 测试 destroy
//         context.close();
    }

}
