package com.fegcocco.emovebackend.dto.ocm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class StationDTO {
    @JsonProperty("ID")
    private Long id;

    @JsonProperty("AddressInfo")
    private AddressInfo addressInfo;

    @JsonProperty("Connections")
    private List<Connection> connections;

    @JsonProperty("UsageCost")
    private String usageCost;

    @Data
    public static class AddressInfo {
        @JsonProperty("Title")
        private String title;
        @JsonProperty("AddressLine1")
        private String addressLine1;
        @JsonProperty("Latitude")
        private Double latitude;
        @JsonProperty("Longitude")
        private Double longitude;
        @JsonProperty("Distance")
        private Double distance;
    }

    @Data
    public static class Connection {
        @JsonProperty("PowerKW")
        private Double powerKW;
        @JsonProperty("Level")
        private Level level;
    }

    @Data
    public static class Level {
        @JsonProperty("Title")
        private String title;
    }
}