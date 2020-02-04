package context.ioc.cycleReference;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author guoxiong
 * 2020/1/31 下午12:57
 */
@Component
public class ClassB {

    @Resource
    private ClassA classA;

    private String name = "classB";

    public ClassB() {
        System.out.println("ClassB construct");
    }

    @PostConstruct
    public void initB() {
        System.out.println("ClassB init");
    }

}
