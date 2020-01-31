package context.ioc.factoryBean;

import org.springframework.context.annotation.Bean;

/**
 * @author guoxiong
 * 2020/1/28 下午5:10
 */
//@Configuration
public class MyFactoryBeanConfiguration {

    /**
     * beanName 为pig, 但最终创建出的实例为 MyFactoryBean.getObject() 方法
     */
    @Bean
    public MyFactoryBean pig() {
        return new MyFactoryBean();
    }

}
