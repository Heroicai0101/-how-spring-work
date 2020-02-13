package context.ioc.autowireMode;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * 注入模型测试
 *
 * @author guoxiong
 * 2020/2/13 下午8:45
 */
@Component
public class MyBeanFactoryPostProcess implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        GenericBeanDefinition bd = (GenericBeanDefinition) beanFactory.getBeanDefinition("myService");
        System.out.println("myService autowireMode: " + bd.getAutowireMode());

        // 改成 AUTOWIRE_BY_NAME 或 AUTOWIRE_BY_TYPE 都会触发set 方法完成依赖注入, 不需要再属性上加 @Resource注解
//        bd.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
//        bd.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

        // 不会触发set方法完成依赖注入; 仅通过触发构造方法完成依赖注入
        bd.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);

        // 注意 MyDao 上不能加 @Component 注解, 否则会出现实例化两次
        RootBeanDefinition myDaBeanDef = new RootBeanDefinition(MyDao.class);

        // 1个参数的构造
        bd.getConstructorArgumentValues().addIndexedArgumentValue(0, "Hello");
        // 2个参数的构造
        bd.getConstructorArgumentValues().addIndexedArgumentValue(1, myDaBeanDef);
    }

}
