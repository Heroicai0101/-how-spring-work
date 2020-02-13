package context.cloudstream;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author guoxiong
 * 2020/2/6 下午7:05
 */
@Component
public class OutputLifecycle implements SmartLifecycle, ApplicationContextAware {

    private volatile boolean running = false;

    private ConfigurableApplicationContext applicationContext;

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        stop();
        if (callback != null) {
            callback.run();
        }
    }

    @Override
    public void start() {
        if (!running) {
            try {
                Map<String, Bindable> bindingService = this.applicationContext.getBeansOfType(Bindable.class);
                for (Bindable bindable : bindingService.values()) {
                    // TODO
                    bindable.bindOutputs();
                }
            } catch (BeansException e) {
                throw new IllegalStateException("Cannot perform binding, no proper implementation found", e);
            }
            this.running = true;
        }
    }


    @Override
    public void stop() {
        if(running) {
            try {
                Map<String, Bindable> bindingService = this.applicationContext.getBeansOfType(Bindable.class);
                for (Bindable bindable : bindingService.values()) {
                    // TODO
                    bindable.unbindOutputs();
                }
            } catch (BeansException e) {
                throw new IllegalStateException("Cannot perform unbinding, no proper implementation found", e);
            }
            this.running = false;
        }
    }


    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getPhase() {
        return Integer.MIN_VALUE + 100;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
