package context.annotation.atEventListener;

import context.ioc.CityDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 事件监听器测试
 *
 * @author guoxiong
 * 2020/1/8 下午10:16
 */
@Component
public class MyEventListener implements InitializingBean {

    private List<CityDao> cityDaos;

    public MyEventListener() {
        System.out.println("1. Listener construct ");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("2. Listener init");
    }


    /**
     * 此方法触发之前，所有的单例bean都是已经完成依赖注入了的
     * Trigger post-initialization callback
     */
    @EventListener(classes = ContextRefreshedEvent.class)
    public void onEvent(ApplicationContextEvent event) {
        System.out.println("3. Listener on event: " + event);
    }

    @Resource
//    @Autowired(required = false)
    public void setxxx(List<CityDao> cityDaos) {
        this.cityDaos = cityDaos;
    }
}
