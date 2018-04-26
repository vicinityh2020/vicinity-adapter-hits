package com.hits.vicinity.adapter.model;

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
        "gatewayMac",
        "name",
        "parkingLotId"
})
public class ParkingGateway {

    @JsonProperty("id")
    private String id;
    @JsonProperty("gatewayMac")
    private String gatewayMac;
    @JsonProperty("name")
    private String name;
    @JsonProperty("parkingLotId")
    private String parkingLotId;
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

    @JsonProperty("gatewayMac")
    public String getGatewayMac() {
        return gatewayMac;
    }

    @JsonProperty("gatewayMac")
    public void setGatewayMac(String gatewayMac) {
        this.gatewayMac = gatewayMac;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("parkingLotId")
    public String getParkingLotId() {
        return parkingLotId;
    }

    @JsonProperty("parkingLotId")
    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
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
        if (!(o instanceof ParkingGateway)) return false;
        ParkingGateway that = (ParkingGateway) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(gatewayMac, that.gatewayMac) &&
                Objects.equals(name, that.name) &&
                Objects.equals(parkingLotId, that.parkingLotId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, gatewayMac, name, parkingLotId);
    }
}