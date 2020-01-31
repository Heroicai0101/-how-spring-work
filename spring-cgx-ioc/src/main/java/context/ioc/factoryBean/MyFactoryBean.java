package context.ioc.factoryBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author guoxiong
 * 2020/1/28 下午5:05
 */
@Component
public class MyFactoryBean implements FactoryBean<Object> {

    @Override
    public Object getObject() throws Exception {
        return new Pig();
    }

    @Override
    public Class<?> getObjectType() {
        return Pig.class;
    }


    @Override
    public boolean isSingleton() {
        return true;
    }
}
