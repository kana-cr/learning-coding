package example.function;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 惰性接口
 *
 * @author kana-cr
 * @date 2023/3/17 20:18
 */

public class Lazy<T> implements Supplier<T> {

    private final Supplier<? extends T> supplier;

    private T value;

    private Lazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    public static <T> Lazy<T> create(Supplier<? extends T> supplier) {
        return new Lazy<T>(supplier);
    }

    @Override
    public T get() {
        if (value == null) {
            T newValue = supplier.get();
            if (newValue == null) {
                throw new IllegalArgumentException("Lazy value cannot be null!");
            }
            value = newValue;
            return newValue;
        }
        return value;
    }

    public <S> Lazy<S> map(Function<? super T, ? extends S> function) {
        return Lazy.create(() -> function.apply(get()));
    }

    public <S> Lazy<S> flatMap(Function<? super T, Lazy<? extends S>> function) {
        return Lazy.create(() -> function.apply(get()).get());
    }

    public static void main(String[] args) {
        Lazy<Integer> integerLazy = Lazy.create(() -> {
            System.out.println("do add");
            return 1 + 1;
        });
        System.out.println(integerLazy.get());
        System.out.println(integerLazy.get());

        Lazy<String> stringLazy = integerLazy.map(String::valueOf);

        Lazy<String> objLazy = integerLazy.flatMap(integer -> stringLazy.map(s -> s + (integer * 2)));

        System.out.println(objLazy.get());
    }
}
