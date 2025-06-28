package com.np.urlShortener.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class AnalyticsResponse {
    public AnalyticsResponse(int clicks) {
        this.clicks = clicks;
    }

    @JsonProperty("clicks")
    private int clicks;

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }
}
