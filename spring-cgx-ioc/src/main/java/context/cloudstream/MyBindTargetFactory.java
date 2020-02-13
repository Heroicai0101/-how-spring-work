package context.cloudstream;

import org.springframework.stereotype.Component;

/**
 * @author guoxiong
 * 2020/2/6 下午5:06
 */
@Component
public class MyBindTargetFactory extends AbstractBindingTargetFactory<MessageChannel> {

    protected MyBindTargetFactory() {
        super(MessageChannel.class);
    }

    public MessageChannel createOutput(String name) {

        return new MessageChannel() {
            @Override
            public boolean send(String xx) {
                System.out.println("do send " + xx);
                return true;
            }
        };
    }

}
