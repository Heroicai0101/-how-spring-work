package context.annotation.atTransaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * spring事务
 * @author guoxiong
 * 2020/2/23 下午4:28
 */
@Component
public class TransactionDemo {

    @Transactional
    public void sayHi() {
        System.out.println("Say hi transaction");
    }

}
