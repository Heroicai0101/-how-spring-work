package context.annotation.atBean;

import javax.annotation.PostConstruct;

public class Car {

    @PostConstruct
    public void init() {
        System.out.println("Car init");
    }

}
