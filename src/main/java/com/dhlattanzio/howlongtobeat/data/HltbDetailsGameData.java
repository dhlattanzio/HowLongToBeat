package com.dhlattanzio.howlongtobeat.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HltbDetailsGameData extends HltbGameData {
    private String description;
    private String alias;
    private String updated;

    private final Map<String, Float> times = new HashMap<>();
    private final List<String> platforms = new ArrayList<>();
    private final List<String> genres = new ArrayList<>();
    private final List<String> developers = new ArrayList<>();
    private final List<String> publishers = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void addTime(String key, float value) {
        times.put(key, value);
    }

    public Map<String, Float> getTimes() {
        return times;
    }

    public void addPlatform(String platform) {
        platforms.add(platform);
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void addGenre(String genre) {
        genres.add(genre);
    }

    public List<String> getGenres() {
        return genres;
    }

    public void addDeveloper(String developer) {
        developers.add(developer);
    }

    public List<String> getDevelopers() {
        return developers;
    }

    public void addPublisher(String publisher) {
        publishers.add(publisher);
    }

    public List<String> getPublishers() {
        return publishers;
    }
}
