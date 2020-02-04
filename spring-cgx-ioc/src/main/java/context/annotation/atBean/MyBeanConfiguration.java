package context.annotation.atBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MyBeanConfiguration {

    @Bean
    public Car car() {
        return new Car();
    }

}
