package com.jw.common.enm;

/**
 * Created by chenliang on 2017/6/7.
 */
public interface CodeEnum<C, T extends Enum<T> & CodeEnum<C, T, V>, V> {
    C getCode();

    String getName();

    V getValue();
}
