package context.ioc.factoryBean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author guoxiong
 * 2020/1/28 下午5:11
 */
@Component
public class FactoryBeanDemo {

    @Resource
    private Pig pig;

    public FactoryBeanDemo() {
        System.out.println("FactoryBeanDemo construct");
    }

    @PostConstruct
    public void init() {
        System.out.println("FactoryBeanDemo init: " + pig.say());
    }

}
