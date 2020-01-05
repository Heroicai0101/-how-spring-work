package context;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author guoxiong
 * 2020/1/3 下午10:53
 */
@Component
@Aspect
public class MyAspect {

    @Pointcut("execution(* context.*.*(..))")
    private void myPointcut() {
    }

    @AfterReturning("myPointcut()")
    public void afterReturningAdvise() {
        System.out.println("-----【AfterReturning】-----");
    }

    @Before("myPointcut()")
    public void beforeAdvise() {
        System.out.println("-----【Before】-----");
    }

    @AfterThrowing("myPointcut()")
    public void afterThrowingAdvise() {
        System.out.println("-----【AfterThrowing】-----");
    }

    @After("myPointcut()")
    public void afterAdvise() {
        System.out.println("-----【After】after -----");
    }

    @Around("myPointcut()")
    public Object aroundAdvise(ProceedingJoinPoint pjp) throws Throwable {
        try {
            System.out.println("-----【Around】do something before -----");
            return pjp.proceed();
        } finally {
            System.out.println("-----【Around】do something after -----");
        }
    }

}
