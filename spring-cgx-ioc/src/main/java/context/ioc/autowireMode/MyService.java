package context.ioc.autowireMode;

import org.springframework.stereotype.Component;

/**
 * @author guoxiong
 * 2020/2/13 下午8:43
 */
@Component
public class MyService {

    private String name;

    //    @Resource
    private MyDao myDao;

    public MyService() {
    }

    public MyService(String name) {
        this.name = name;
    }

    public MyService(String name, MyDao myDao) {
        this.name = name;
        this.myDao = myDao;
    }

    public MyDao getMyDao() {
        return myDao;
    }

    public void setMyDao(MyDao myDao) {
        this.myDao = myDao;
    }

    @Override
    public String toString() {
        return "MyService{" +
                "name='" + name + '\'' +
                ", myDao=" + myDao +
                '}';
    }

}
