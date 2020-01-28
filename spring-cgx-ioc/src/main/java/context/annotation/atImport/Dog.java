package context.annotation.atImport;

import javax.annotation.PostConstruct;

public class Dog {

    @PostConstruct
    public void init() {
        System.out.println("Dog wang wang");
    }

}
