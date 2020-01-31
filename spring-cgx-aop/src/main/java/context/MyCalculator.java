package context;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public /*final*/ class MyCalculator /*implements IMyCalculator*/ {

    public MyCalculator() {
        System.out.println("execute construct");
    }

    @PostConstruct
    void init() {
        System.out.println("execute init");
    }

    public void div(int a, int b) {
        System.out.print("execute div method: ");
        int res = a / b;
        System.out.println(String.format("%s / %s = %s", a, b, res));
    }

}
