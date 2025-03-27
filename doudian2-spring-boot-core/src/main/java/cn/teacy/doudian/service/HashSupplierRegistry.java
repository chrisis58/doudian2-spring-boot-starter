package cn.teacy.doudian.service;

import cn.teacy.common.interfaces.SupplierRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class HashSupplierRegistry<K, V> implements SupplierRegistry<K, V> {

    private final Map<K, Supplier<V>> registry = new HashMap<>();

    @Override
    public V eval(K index) {
        return Optional.ofNullable(registry.get(index))
                .map(Supplier::get)
                .orElse(null);
    }

    @Override
    public void register(K index, Supplier<V> supplier) {
        registry.put(index, supplier);
    }

    public static void main(String[] args) {
        HashSupplierRegistry<String, String> registry = new HashSupplierRegistry<>();
        registry.register("time", () -> String.valueOf(System.currentTimeMillis()));
        registry.register("inc", new Supplier<>() {
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
    }

}
