package com.hits.vicinity.adapter.domain.pni;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "description",
        "streetAddress",
        "latitude",
        "longitude",
        "cameraId"
})
public class ParkingLot {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("streetAddress")
    private String streetAddress;
    @JsonProperty("latitude")
    private double latitude;
    @JsonProperty("longitude")
    private double longitude;
    @JsonProperty("cameraId")
    private String cameraId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("streetAddress")
    public String getStreetAddress() {
        return streetAddress;
    }

    @JsonProperty("streetAddress")
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @JsonProperty("latitude")
    public double getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public double getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("cameraId")
    public String getCameraId() {
        return cameraId;
    }

    @JsonProperty("cameraId")
    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingLot)) return false;
        ParkingLot that = (ParkingLot) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(streetAddress, that.streetAddress) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(cameraId, that.cameraId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, streetAddress, latitude, longitude, cameraId);
    }

}