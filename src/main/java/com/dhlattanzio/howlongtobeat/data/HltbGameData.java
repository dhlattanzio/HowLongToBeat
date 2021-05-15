package com.dhlattanzio.howlongtobeat.data;

import java.util.HashMap;
import java.util.Map;

public class HltbGameData {
    private String id;
    private String imageUrl;
    private String name;
    private final Map<String, Float> times = new HashMap<>();

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTime(String key, float value) {
        times.put(key, value);
    }

    public Map<String, Float> getTimes() {
        return times;
    }
}
