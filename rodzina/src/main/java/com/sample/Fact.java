package com.sample;

public class Fact {
    private String name;
    private boolean booleanValue;
    private String stringValue;
    private int intValue;

    public Fact(String name) {
        this.name = name;
    }

    public Fact(String name, boolean booleanValue) {
        this(name);
        this.booleanValue = booleanValue;
    }

    public Fact(String name, String stringValue) {
        this(name);
        this.stringValue = stringValue;
    }

    public Fact(String name, int intValue) {
        this(name);
        this.intValue = intValue;
    }

    public boolean name(String value) {
        return name.equals(value);
    }

    public void setValue(boolean value) {
        booleanValue = value;
    }

    public boolean value(boolean value) {
        return booleanValue == value;
    }

    public boolean value(String value) {
        return value.equals(stringValue);
    }

    public boolean value(int value) {
        return value == intValue;
    }

    public boolean intValueLessThan(int value) {
        return intValue < value;
    }

    public boolean intValueGreaterThan(int value) {
        return intValue > value;
    }
}
