package cn.teacy.common.register;

import cn.teacy.common.interfaces.SupplierRegistry;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class HashSupplierRegistry<K, V> implements SupplierRegistry<K, V> {

    private final Map<K, Supplier<V>> registry = new HashMap<>();

    @Nullable
    @Override
    public V eval(K index) {
        return Optional.ofNullable(index)
                .map(registry::get)
                .map(Supplier::get)
                .orElse(null);
    }

    @Override
    public HashSupplierRegistry<K, V> register(@NonNull K index, @NonNull Supplier<V> supplier) {
        registry.put(index, supplier);
        return this;
    }

    public static void main(String[] args) {
        HashSupplierRegistry<String, String> registry = new HashSupplierRegistry<>();
        registry.register("time", () -> String.valueOf(System.currentTimeMillis()))
                .register("inc", new Supplier<>() {
                    private int i = 0;

                    @Override
                    public String get() {
                        return String.valueOf(i++);
                    }
                });

        System.out.println(registry.eval("time"));

        System.out.println(registry.eval("inc"));
        System.out.println(registry.eval("inc"));

        System.out.println(registry.eval("none-exist"));
        System.out.println(registry.eval(null));
    }

}
