package cn.teacy.common.interfaces;

import java.util.function.Supplier;

public interface SupplierRegistry<K, V> {

    V eval(K index);

    SupplierRegistry<K, V> register(K index, Supplier<V> supplier);

}
