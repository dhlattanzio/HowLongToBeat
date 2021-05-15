package com.dhlattanzio.howlongtobeat;

import com.dhlattanzio.howlongtobeat.data.HltbDetailsGameData;
import com.dhlattanzio.howlongtobeat.data.HltbListData;
import com.dhlattanzio.howlongtobeat.parser.HltbBasicParser;
import com.dhlattanzio.howlongtobeat.parser.HltbParser;

public class HowLongToBeatService {
    public static final String URL_BASE="https://howlongtobeat.com";
    public static final String URL_SEARCH=URL_BASE+"/search_results.php";
    public static final String URL_GAME=URL_BASE+"/game?id=";

    private final HltbParser dataParser;

    public HowLongToBeatService() {
        this.dataParser=new HltbBasicParser();
    }

    public HltbListData search(HltbRequest request) {
        String result = RequestPostForm.request(URL_SEARCH, request.getParameters());
        return dataParser.parseSearch(result);
    }

    public HltbDetailsGameData details(String id) {
        String result = RequestPostForm.request(URL_GAME + id);
        return dataParser.parseDetails(result, id);
    }
}