package com.dhlattanzio.howlongtobeat.options;

public enum HltbTimes {
    MAIN_STORY              ("Main Story"),
    MAIN_PLUS_EXTRAS        ("Main + Extras"),
    COMPLETIONIST           ("Completionist"),
    ALL_STYLES              ("All Styles"),
    ;

    private final String name;
    HltbTimes(String name) {
        this.name=name;
    }

    @Override
    public String toString() {
        return name;
    }
}
