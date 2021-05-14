package com.dhlattanzio.howlongtobeat.options;

public enum HltbOrder {
    NORMAL_ORDER    ("Normal Order"),
    REVERSE_ORDER   ("Reverse Order"),
    ;

    private final String name;
    HltbOrder(String name) {
        this.name=name();
    }

    @Override
    public String toString() {
        return name;
    }
}
