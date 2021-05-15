package com.dhlattanzio.howlongtobeat.parser;

import com.dhlattanzio.howlongtobeat.data.HltbDetailsGameData;
import com.dhlattanzio.howlongtobeat.data.HltbListData;

public interface HltbParser {
    HltbListData parseSearch(String data);
    HltbDetailsGameData parseDetails(String data, String gameId);
}
