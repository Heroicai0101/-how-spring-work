package context.annotation.atImport;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

public class ImportDemo {

    @Resource
    private Person person;

    public ImportDemo() {
        System.out.println("@Import object construct");
    }

    @PostConstruct
    public void init() {
        System.out.println("@Import object init " + person.getName());
    }

}
