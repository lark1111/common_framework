package com.imindera.wgy.util;

public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}