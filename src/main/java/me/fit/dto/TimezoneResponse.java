package me.fit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimezoneResponse {
    @JsonProperty("timeZone")
    public String timeZone;

    @JsonProperty("currentLocalTime")
    public String currentLocalTime;
}
