package context;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author guoxiong
 * 2019/12/21 下午9:20
 */
@Component
public class CityService {

    @Resource
    private CityDao cityDao;

    public CityService() {
        System.out.println("construct");
    }

    @PostConstruct
    void init() {
        System.out.println("init");
    }

    public void hello() {
        cityDao.hello();
    }

}
