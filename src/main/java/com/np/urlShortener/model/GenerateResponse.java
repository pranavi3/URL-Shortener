package com.np.urlShortener.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GenerateResponse {

    @JsonProperty("shortCode")
    private String shortCode;

    public GenerateResponse() {

    }
    public GenerateResponse(String shortCode) {
        this.shortCode = shortCode;
    }


    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
}
