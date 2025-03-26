package cn.teacy.common.interfaces;

public interface TokenHolder<T> {

    void setToken(String token);

    T getToken();

    void clear();

}
