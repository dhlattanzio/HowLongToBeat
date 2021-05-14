package com.dhlattanzio.howlongtobeat;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Optional;

public class HowLongToBeatService {
    private static final String URL_BASE="https://howlongtobeat.com";
    private static final String URL_SEARCH=URL_BASE+"/search_results.php";
    private static final String URL_GAME=URL_BASE+"/game?id=";

    public float gameTimeStringToFloat(String gameTime) {
        String[] splitted = gameTime.split(" ");
        boolean isMinutes = splitted[1].equalsIgnoreCase("Mins");
        
        String timeValue = splitted[0].replace("\u00bd", ".5");
        if (timeValue.matches("[0-9.]+")) {
            return Float.parseFloat(timeValue) / (isMinutes ? 60 : 1);
        }
        return -1;
    }

    public JSONObject search(HltbRequest request) {
        String result = RequestPostForm.request(URL_SEARCH, request.getParameters());

        JSONObject jsonResult = new JSONObject();
        Document document = Jsoup.parseBodyFragment(result);
        Elements elements = document.select("li");

        String totalGamesFound = document.body()
                .getElementsByTag("div").first()
                .getElementsByTag("h3").first()
                .text().split(" ")[2];

        jsonResult.put("currentPage", 0);
        jsonResult.put("totalPages", 0);
        jsonResult.put("totalGamesFound", totalGamesFound);

        JSONArray jsonArrayGameList = new JSONArray();

        // List of all games
        for(Element item : elements) {
            JSONObject jsonGameItem = new JSONObject();

            Element elementName = item.getElementsByClass("search_list_details").first()
                    .getElementsByTag("h3").first()
                    .getElementsByTag("a").first();
            Elements elementsTimes = item.getElementsByClass("search_list_tidbit");

            String imageUrl = item.getElementsByClass("search_list_image").first().getElementsByTag("img").attr("src");
            String gameId = elementName.attr("href").split("=")[1];
            String gameName = elementName.text();

            JSONObject jsonGameTimes = new JSONObject();
            for(int i=0;i<elementsTimes.size();i+=2) {
                String timeName = elementsTimes.get(i).text();

                float timeValue = gameTimeStringToFloat(elementsTimes.get(i+1).text());
                if (timeValue >= 0) {
                    jsonGameTimes.put(timeName, timeValue);
                }
            }

            jsonGameItem.put("imageUrl", URL_BASE + imageUrl);
            jsonGameItem.put("id", gameId);
            jsonGameItem.put("name", gameName);
            jsonGameItem.put("times", jsonGameTimes);

            jsonArrayGameList.put(jsonGameItem);
        }
        jsonResult.put("games", jsonArrayGameList);

        return jsonResult;
    }

    public JSONObject details(String id) {
        String result = RequestPostForm.request(URL_GAME + id);
        JSONObject jsonResult = new JSONObject();

        // Document & elements
        Document document = Jsoup.parse(result);
        Element elementGameTimes = document.getElementsByClass("game_times").first();
        Elements elementsGameTimesList = elementGameTimes.getElementsByTag("li");
        Elements elementsProfileInfo = document.getElementsByClass("profile_info");

        // Game Data
        String gameName = document.getElementsByClass("profile_header").first().text();
        String imageUrl = URL_BASE + document.getElementsByClass("game_image").first()
                                                .getElementsByTag("img").first()
                                                .attr("src");
        String gameDescription = elementsProfileInfo.first().text();
        String[] gamePlatforms = elementsProfileInfo.get(1).text().replace("Platforms: ", "").split(", ");
        String[] gameGenres = elementsProfileInfo.get(2).text().replace("Genres: ", "").split(", ");
        String gameDeveloper = elementsProfileInfo.get(3).text().replace("Developer: ", "");
        String gamePublisher = elementsProfileInfo.get(4).text().replace("Publisher: ", "");

        Optional<Element> elementGameAlias = elementsProfileInfo.stream()
                .filter((element -> element.text().startsWith("Alias: ")))
                .findFirst();
        String gameAlias = elementGameAlias.map(value -> value.text().replace("Alias: ", "")).orElse("");

        Optional<Element> elementPageUpdated = elementsProfileInfo.stream()
                .filter((element -> element.text().startsWith("Updated: ")))
                .findFirst();
        String pageUpdated = elementPageUpdated.map(value -> value.text().replace("Updated: ", "")).orElse("");

        // Times
        JSONObject jsonGameTimes = new JSONObject();
        for(Element element : elementsGameTimesList) {
            String timeName = element.child(0).text();
            float timeValue = gameTimeStringToFloat(element.child(1).text());

            if (timeValue >= 0) {
                jsonGameTimes.put(timeName, timeValue);
            }
        }

        jsonResult.put("id", id);
        jsonResult.put("imageUrl", imageUrl);
        jsonResult.put("name", gameName);
        jsonResult.put("description", gameDescription);
        jsonResult.put("platforms", new JSONArray(gamePlatforms));
        jsonResult.put("developer", gameDeveloper);
        jsonResult.put("publisher", gamePublisher);
        jsonResult.put("genres", new JSONArray(gameGenres));
        if (!gameAlias.isEmpty()) jsonResult.put("alias", gameAlias);
        jsonResult.put("updated", pageUpdated);
        jsonResult.put("times", jsonGameTimes);

        return jsonResult;
    }
}