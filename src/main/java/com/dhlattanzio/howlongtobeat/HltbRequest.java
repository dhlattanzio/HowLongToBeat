package com.dhlattanzio.howlongtobeat;

import com.dhlattanzio.howlongtobeat.options.HltbOrder;
import com.dhlattanzio.howlongtobeat.options.HltbSort;

import java.util.HashMap;
import java.util.Map;

public class HltbRequest {
    private final Map<String, String> parameters=new HashMap<>();

    public HltbRequest() {
        parameters.put("t", "games");
        parameters.put("length_type", "main");

        setName("");
        setSortBy(HltbSort.MOST_POPULAR);
        setOrderBy(HltbOrder.NORMAL_ORDER);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setName(String name) {
        parameters.put("queryString", name);
    }

    public void setSortBy(HltbSort sortType) {
        parameters.put("sorthead", sortType.toString());
    }

    public void setOrderBy(HltbOrder orderType) {
        parameters.put("sortid", orderType.toString());
    }

    public static class Builder {
        private final HltbRequest request=new HltbRequest();

        public Builder name(String name) {
            request.setName(name);
            return this;
        }

        public Builder sortBy(HltbSort sort) {
            request.setSortBy(sort);
            return this;
        }

        public Builder orderBy(HltbOrder order) {
            request.setOrderBy(order);
            return this;
        }

        public HltbRequest build() {
            return request;
        }

    }
}
