package context.annotation.atImport;

import org.springframework.stereotype.Component;

/**
 * @author guoxiong
 * 2020/1/23 下午6:01
 */
@Component
public class Person {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
