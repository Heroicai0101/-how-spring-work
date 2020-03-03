package context.annotation.atImport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 1、通过 @Import 向ioc导入某个类时, 如果类上有 @Configuration 注解, 则ioc容器中会存在该类的bean定义; 否则, 不存在;
 * 2、无论是否存在, 周期回调方法都会触发, why? 并且 @Bean 也会起作用
 *
 * @author guoxiong
 * 2020/2/26 下午8:33
 */
@Configuration
public class MyImportConfigurationClass {

    @PostConstruct
    public void init() {
        System.out.println("MyImportConfigurationClass init");
    }

    @Bean
    public Marker marker() {
        return new Marker();
    }

    class Marker {
        Marker() {
        }
    }

}
