package context.ioc.autowireMode;

import javax.annotation.PostConstruct;

/**
 * @author guoxiong
 * 2020/2/13 下午8:43
 */
//@Component
public class MyDao {

    public MyDao() {
        System.out.println("MyDao construct");
    }

    @PostConstruct
    public void init() {
        System.out.println("MyDao init");
    }

}
