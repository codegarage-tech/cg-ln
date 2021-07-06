package com.meembusoft.realmmanager;

public class MutableVariable<T> {

    private String name;
    private T value;

    public MutableVariable(String name, T value) {
        this.name = name;
        setValue(value);
    }

    public MutableVariable(T value) {
        this.name = System.currentTimeMillis() + "";
        setValue(value);
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public MutableVariable setName(String name) {
        this.name = name;
        return this;
    }

    public MutableVariable setValue(T value) {
        this.value = value;
        return this;
    }

    public MutableVariable reAssign(String name, T value) {
        this.name = name;
        this.value = value;
        return this;
    }
}