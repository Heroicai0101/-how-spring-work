package context.annotation.atImport;

import javax.annotation.PostConstruct;

public class Cat {

    @PostConstruct
    public void init() {
        System.out.println("Cat miao miao");
    }

}
