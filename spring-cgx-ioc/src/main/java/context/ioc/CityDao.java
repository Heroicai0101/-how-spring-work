package context.ioc;

import org.springframework.stereotype.Component;

/**
 * @author guoxiong
 * 2019/12/21 下午11:07
 */
@Component
public class CityDao {

    public void query() {
        System.out.println("CityDao query");
    }
}
