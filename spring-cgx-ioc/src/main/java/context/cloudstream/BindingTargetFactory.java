package context.cloudstream;

public interface BindingTargetFactory {

    boolean canCreate(Class<?> clazz);

    Object createOutput(String name);

}