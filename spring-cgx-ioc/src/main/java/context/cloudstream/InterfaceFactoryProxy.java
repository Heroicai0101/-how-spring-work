package context.cloudstream;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author guoxiong
 * 2020/2/5 下午10:13
 */
public class InterfaceFactoryProxy implements MethodInterceptor, FactoryBean<Object>, InitializingBean, Bindable {

    private final Map<Method, Object> cache = new ConcurrentHashMap<Method, Object>();

    private final Map<String, BoundTargetHolder> outputHolders = new HashMap<String, BoundTargetHolder>();

    private final Class<?> type;

    private Object proxy;

    @Resource
    private Map<String, BindingTargetFactory> bindTargetFactories;

    public InterfaceFactoryProxy(Class<?> type) {
        this.type = type;
    }

    @Override
    public synchronized Object getObject() throws Exception {
        if (this.proxy == null) {
            ProxyFactory factory = new ProxyFactory(this.type, this);
            this.proxy = factory.getProxy();
        }
        return this.proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();

        Object binder = cache.get(method);
        if (binder != null) {
            return binder;
        }

//        Input input = AnnotationUtils.findAnnotation(method, Input.class);
//        if (input != null) {
//            MessageChannel messageChannel = new MessageChannel() {
//                @Override
//                public boolean send(String xx) {
//                    System.out.println("send " + xx);
//                    return true;
//                }
//            };
//            cache.put(method, messageChannel);
//            return messageChannel;
//        }
        // 直接返回null, 表明不允许调用除 @Output 注解的方法之外的其他方法
        return null;
//        return invocation.proceed();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ReflectionUtils.doWithMethods(this.type, new ReflectionUtils.MethodCallback() {

            @Override
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                Output output = AnnotationUtils.findAnnotation(method, Output.class);
                if (output != null) {
                    Class<?> returnType = method.getReturnType();
                    BindingTargetFactory bindingTargetFactory = InterfaceFactoryProxy.this.getBindingTargetFactory
                            (returnType);
                    Object messageChannel = bindingTargetFactory.createOutput(output.value());
                    // 加入到缓存
                    cache.put(method, messageChannel);

                    // 管理起来
                    outputHolders.put(output.value(), new BoundTargetHolder(messageChannel, true));
                }
            }
        });

    }

    // 检查下是否有多个Factory, 一种类型只允许有一个
    private BindingTargetFactory getBindingTargetFactory(Class<?> bindingTargetType) {
        List<String> candidateBindingTargetFactories = new ArrayList<String>();
        for (Map.Entry<String, BindingTargetFactory> bindingTargetFactoryEntry : this.bindTargetFactories.entrySet()) {
            if (bindingTargetFactoryEntry.getValue().canCreate(bindingTargetType)) {
                candidateBindingTargetFactories.add(bindingTargetFactoryEntry.getKey());
            }
        }
        if (candidateBindingTargetFactories.size() == 1) {
            return this.bindTargetFactories.get(candidateBindingTargetFactories.get(0));
        } else {
            if (candidateBindingTargetFactories.size() == 0) {
                throw new IllegalStateException("No factory found for binding target type: "
                        + bindingTargetType.getName() + " among registered factories: "
                        + StringUtils.collectionToCommaDelimitedString(bindTargetFactories.keySet()));
            } else {
                throw new IllegalStateException(
                        "Multiple factories found for binding target type: " + bindingTargetType.getName() + ": "
                                + StringUtils.collectionToCommaDelimitedString(candidateBindingTargetFactories));
            }
        }
    }

    @Override
    public void bindOutputs() {
        for (Map.Entry<String, BoundTargetHolder> boundTargetHolderEntry : this.outputHolders.entrySet()) {
            BoundTargetHolder boundTargetHolder = boundTargetHolderEntry.getValue();
            String outputTargetName = boundTargetHolderEntry.getKey();
            if (boundTargetHolderEntry.getValue().isBindable()) {
//                System.out.println("yes====");
                // 比如启动生产者
//                bindingService.bindProducer(boundTargetHolder.getBoundTarget(), outputTargetName);
            }
        }
    }

    @Override
    public void unbindOutputs() {
        for (Map.Entry<String, BoundTargetHolder> boundTargetHolderEntry : this.outputHolders.entrySet()) {
            BoundTargetHolder boundTargetHolder = boundTargetHolderEntry.getValue();
            String outputTargetName = boundTargetHolderEntry.getKey();
            if (boundTargetHolderEntry.getValue().isBindable()) {
//                System.out.println("no====");
                // 比如停止生产者
//                bindingService.bindProducer(boundTargetHolder.getBoundTarget(), outputTargetName);
            }
        }
    }


    private final class BoundTargetHolder {

        private Object boundTarget;

        private boolean bindable;

        private BoundTargetHolder(Object boundTarget, boolean bindable) {
            this.boundTarget = boundTarget;
            this.bindable = bindable;
        }

        public Object getBoundTarget() {
            return this.boundTarget;
        }

        public boolean isBindable() {
            return this.bindable;
        }
    }
}
