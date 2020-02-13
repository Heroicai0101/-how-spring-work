package context.cloudstream;

public abstract class AbstractBindingTargetFactory<T> implements BindingTargetFactory {

    private final Class<T> bindingTargetType;

    public AbstractBindingTargetFactory(Class<T> bindingTargetType) {
        this.bindingTargetType = bindingTargetType;
    }

    @Override
    public final boolean canCreate(Class<?> clazz) {
        return clazz.isAssignableFrom(this.bindingTargetType);
    }

    @Override
    public abstract T createOutput(String name);

}