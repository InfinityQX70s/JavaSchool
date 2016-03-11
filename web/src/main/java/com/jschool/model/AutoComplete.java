package com.jschool.model;

import java.util.List;

/**
 * Created by infinity on 10.03.16.
 */
public class AutoComplete {

    private String query;
    private List<String> suggestions;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}
