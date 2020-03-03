package context.annotation.atPropertySource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@PropertySource(value = "classpath:demo/demo.properties", encoding = "utf-8", ignoreResourceNotFound = false)
public class PropertySourceDemo {

    @Value("${name:xman}")
    private String name;

    @Value("${age:0}")
    private int age;

    /** 实现类为 StandardEnvironment, 持有一个 ConfigurablePropertyResolver 对象 */
    @Resource
    private Environment environment;

    @PostConstruct
    public void init() {
        String s = environment.resolveRequiredPlaceholders("${name}");
        String name = environment.getProperty("name");

        System.out.println("name=" + this.name + ", age=" + age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
