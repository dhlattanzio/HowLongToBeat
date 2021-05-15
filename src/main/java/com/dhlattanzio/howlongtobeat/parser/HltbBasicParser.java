package com.dhlattanzio.howlongtobeat.parser;

import com.dhlattanzio.howlongtobeat.HowLongToBeatService;
import com.dhlattanzio.howlongtobeat.data.HltbDetailsGameData;
import com.dhlattanzio.howlongtobeat.data.HltbGameData;
import com.dhlattanzio.howlongtobeat.data.HltbListData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.Optional;

public class HltbBasicParser implements HltbParser {
    @Override
    public HltbListData parseSearch(String data) {
        HltbListData hltbListData=new HltbListData();

        Document document = Jsoup.parseBodyFragment(data);
        Elements elementsGames = document.select("li");

        String totalGamesFound = document.body()
                .getElementsByTag("div").first()
                .getElementsByTag("h3").first()
                .text().split(" ")[2];

        hltbListData.setTotalGamesFound(Integer.parseInt(totalGamesFound));

        // List of all games
        for(Element item : elementsGames) {
            HltbGameData hltbGameData=new HltbGameData();

            Element elementName = item.getElementsByClass("search_list_details").first()
                    .getElementsByTag("h3").first()
                    .getElementsByTag("a").first();
            Elements elementsTimes = item.getElementsByClass("search_list_tidbit");

            hltbGameData.setImageUrl(HowLongToBeatService.URL_BASE +
                    item.getElementsByClass("search_list_image").first().getElementsByTag("img").attr("src"));
            hltbGameData.setId(elementName.attr("href").split("=")[1]);
            hltbGameData.setName(elementName.text());

            for(int i=0;i<elementsTimes.size();i+=2) {
                String timeName = elementsTimes.get(i).text();

                float timeValue = gameTimeStringToFloat(elementsTimes.get(i+1).text());
                if (timeValue >= 0) {
                    hltbGameData.addTime(timeName, timeValue);
                }
            }

            hltbListData.addGame(hltbGameData);
        }

        return hltbListData;
    }

    @Override
    public HltbDetailsGameData parseDetails(String data, String gameId) {
        HltbDetailsGameData hltbDetailsGameData = new HltbDetailsGameData();

        // Document & elements
        Document document = Jsoup.parse(data);
        Element elementGameTimes = document.getElementsByClass("game_times").first();
        Elements elementsGameTimesList = elementGameTimes.getElementsByTag("li");
        Elements elementsProfileInfo = document.getElementsByClass("profile_info");

        // Game Data
        String gameName = document.getElementsByClass("profile_header").first().text();
        String imageUrl = HowLongToBeatService.URL_BASE + document.getElementsByClass("game_image").first()
                .getElementsByTag("img").first()
                .attr("src");
        String gameDescription = elementsProfileInfo.first().text().replace("..Read More", "");
        String[] gamePlatforms = getProfileInfo(elementsProfileInfo.get(1).text());
        String[] gameGenres = getProfileInfo(elementsProfileInfo.get(2).text());
        String[] gameDevelopers = getProfileInfo(elementsProfileInfo.get(3).text());
        String[] gamePublishers = getProfileInfo(elementsProfileInfo.get(4).text());

        Optional<Element> elementGameAlias = elementsProfileInfo.stream()
                .filter((element -> element.text().startsWith("Alias: ")))
                .findFirst();
        String gameAlias = elementGameAlias.map(value -> value.text().replace("Alias: ", "")).orElse("");

        Optional<Element> elementPageUpdated = elementsProfileInfo.stream()
                .filter((element -> element.text().startsWith("Updated: ")))
                .findFirst();
        String pageUpdated = elementPageUpdated.map(value -> value.text().replace("Updated: ", "")).orElse("");

        // Times
        for(Element element : elementsGameTimesList) {
            String timeName = element.child(0).text();
            float timeValue = gameTimeStringToFloat(element.child(1).text());

            if (timeValue >= 0) {
                hltbDetailsGameData.addTime(timeName, timeValue);
            }
        }

        hltbDetailsGameData.setId(gameId);
        hltbDetailsGameData.setImageUrl(imageUrl);
        hltbDetailsGameData.setName(gameName);
        hltbDetailsGameData.setDescription(gameDescription);
        hltbDetailsGameData.setAlias(gameAlias);
        hltbDetailsGameData.setUpdated(pageUpdated);
        Arrays.stream(gamePlatforms).forEach(hltbDetailsGameData::addPlatform);
        Arrays.stream(gameGenres).forEach(hltbDetailsGameData::addGenre);
        Arrays.stream(gameDevelopers).forEach(hltbDetailsGameData::addDeveloper);
        Arrays.stream(gamePublishers).forEach(hltbDetailsGameData::addPublisher);

        return hltbDetailsGameData;
    }

    public String[] getProfileInfo(String info) {
        String[] split=info.split(": ");
        if (split.length==1) return split;
        return split[1].split(", ");
    }

    public float gameTimeStringToFloat(String gameTime) {
        String[] split = gameTime.split(" ");
        if (split.length<2) return -1;

        boolean isMinutes = split[1].equalsIgnoreCase("Mins");

        String timeValue = split[0].replace("\u00bd", ".5");
        if (timeValue.matches("[0-9.]+")) {
            return Float.parseFloat(timeValue) / (isMinutes ? 60 : 1);
        }
        return -1;
    }
}
