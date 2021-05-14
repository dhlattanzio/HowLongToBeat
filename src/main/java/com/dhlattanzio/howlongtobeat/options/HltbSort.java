package com.dhlattanzio.howlongtobeat.options;

public enum HltbSort {
    NAME                ("Name"),
    MAIN_STORY          ("Main Story"),
    MAIN_PLUS_EXTRAS    ("mainp"),
    COMPLETIONIST       ("Completionist"),
    AVERAGE_TIME        ("Average Time"),
    TOP_RATED           ("Top Rated"),
    MOST_POPULAR        ("popular"),
    MOST_BACKLOGS       ("Most Backlogs"),
    MOST_SUBMISSIONS    ("Most Submissions"),
    MOST_PLAYED         ("Most Played"),
    MOST_SPEEDRUNS      ("Most Speedruns"),
    RELEASE_DATE        ("Release Dates")
    ;

    private final String name;
    HltbSort(String name) {
        this.name=name;
    }

    @Override
    public String toString() {
        return name;
    }
}
