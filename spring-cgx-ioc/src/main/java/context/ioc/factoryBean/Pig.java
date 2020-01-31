package context.ioc.factoryBean;

/**
 * @author guoxiong
 * 2020/1/28 下午5:06
 */
public class Pig {

    private String name;

    public Pig() {
        this.name = "pig";
    }

    public String say() {
        return "Pig pig";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pig{" +
                "name='" + name + '\'' +
                '}';
    }

}
