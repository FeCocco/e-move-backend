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

    @JsonProperty("OperatorInfo")
    private OperatorInfo operatorInfo;

    @JsonProperty("UsageType")
    private UsageType usageType;

    @JsonProperty("StatusType")
    private StatusType statusType;

    @JsonProperty("UserComments")
    private List<UserComment> userComments;

    @JsonProperty("GeneralComments")
    private String generalComments;

    @JsonProperty("NumberOfPoints")
    private Integer numberOfPoints;

    @Data
    public static class AddressInfo {
        @JsonProperty("Title")
        private String title;
        @JsonProperty("AddressLine1")
        private String addressLine1;
        @JsonProperty("Town")
        private String town;
        @JsonProperty("StateOrProvince")
        private String stateOrProvince;
        @JsonProperty("Postcode")
        private String postcode;
        @JsonProperty("Latitude")
        private Double latitude;
        @JsonProperty("Longitude")
        private Double longitude;
        @JsonProperty("Distance")
        private Double distance;
        @JsonProperty("AccessComments")
        private String accessComments;
        @JsonProperty("ContactTelephone1")
        private String contactTelephone1;
    }

    @Data
    public static class Connection {
        @JsonProperty("PowerKW")
        private Double powerKW;
        @JsonProperty("Level")
        private Level level;
        @JsonProperty("ConnectionType")
        private ConnectionType connectionType;
        @JsonProperty("Quantity")
        private Integer quantity;
    }

    @Data
    public static class Level {
        @JsonProperty("Title")
        private String title;
        @JsonProperty("IsFastChargeCapable")
        private Boolean isFastChargeCapable;
    }

    @Data
    public static class ConnectionType {
        @JsonProperty("Title")
        private String title;
    }

    @Data
    public static class OperatorInfo {
        @JsonProperty("Title")
        private String title;
        @JsonProperty("WebsiteURL")
        private String websiteURL;
        @JsonProperty("PhonePrimaryContact")
        private String phonePrimaryContact;
        @JsonProperty("ContactEmail")
        private String contactEmail;
    }

    @Data
    public static class UsageType {
        @JsonProperty("Title")
        private String title;
        @JsonProperty("IsPayAtLocation")
        private Boolean isPayAtLocation;
        @JsonProperty("IsMembershipRequired")
        private Boolean isMembershipRequired;
    }

    @Data
    public static class StatusType {
        @JsonProperty("Title")
        private String title;
        @JsonProperty("IsOperational")
        private Boolean isOperational;
    }

    @Data
    public static class UserComment {
        @JsonProperty("UserName")
        private String userName;
        @JsonProperty("Comment")
        private String comment;
        @JsonProperty("Rating")
        private Integer rating;
        @JsonProperty("DateCreated")
        private String dateCreated;
        @JsonProperty("CheckinStatusType")
        private CheckinStatusType checkinStatusType;
    }

    @Data
    public static class CheckinStatusType {
        @JsonProperty("Title")
        private String title;
        @JsonProperty("IsPositive")
        private Boolean isPositive;
    }
}