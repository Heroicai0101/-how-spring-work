package context.ioc.cycleReference;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author guoxiong
 * 2020/1/31 下午12:57
 */
@Component
public class ClassA {

    @Resource
    private ClassB classB;

    private String name = "classA";

    public ClassA() {
        System.out.println("ClassA construct");
    }

    @PostConstruct
    public void initA() {
        System.out.println("ClassA init");
    }

}
