package com.dhlattanzio.howlongtobeat.data;

import java.util.ArrayList;
import java.util.List;

public class HltbListData {
    private int currentPage;
    private int totalPages;
    private int totalGamesFound;

    private final List<HltbGameData> games=new ArrayList<>();

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalGamesFound(int totalGamesFound) {
        this.totalGamesFound = totalGamesFound;
    }

    public int getTotalGamesFound() {
        return totalGamesFound;
    }

    public void addGame(HltbGameData game) {
        this.games.add(game);
    }

    public List<HltbGameData> getGames() {
        return games;
    }
}