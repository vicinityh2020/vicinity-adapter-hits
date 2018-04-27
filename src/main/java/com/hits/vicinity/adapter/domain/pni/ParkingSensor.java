package com.hits.vicinity.adapter.domain.pni;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sensorId",
        "parkingSpace",
        "parkingLot",
        "parkingLotId",
        "network",
        "lat",
        "lon",
        "mode",
        "status",
        "carPresence",
        "carCounter",
        "serverTime",
        "gatewayTime",
        "sentralTime",
        "rssi",
        "snr",
        "temperature",
        "battery"
})
public class ParkingSensor {

    @JsonProperty("sensorId")
    private String sensorId;

    @JsonProperty("parkingSpace")
    private String parkingSpace;

    @JsonProperty("parkingLot")
    private String parkingLot;

    @JsonProperty("parkingLotId")
    private String parkingLotId;

    @JsonProperty("network")
    private String network;

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("lon")
    private Double lon;

    @JsonProperty("mode")
    private String mode;

    @JsonProperty("status")
    private String status;

    @JsonProperty("carPresence")
    private Integer carPresence;

    @JsonProperty("carCounter")
    private Integer carCounter;

    @JsonProperty("serverTime")
    private String serverTime;

    @JsonProperty("gatewayTime")
    private String gatewayTime;

    @JsonProperty("sentralTime")
    private Integer sentralTime;

    // received signal strength indicator: https://en.wikipedia.org/wiki/Received_signal_strength_indication
    @JsonProperty("rssi")
    private Integer rssi;

    // signal-to-noise: https://en.wikipedia.org/wiki/Signal-to-noise_ratio_(imaging)
    @JsonProperty("snr")
    private Integer snr;

    @JsonProperty("temperature")
    private Double temperature;

    @JsonProperty("battery")
    private Integer battery;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sensorId")
    public String getSensorId() {
        return sensorId;
    }

    @JsonProperty("sensorId")
    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    @JsonProperty("parkingSpace")
    public String getParkingSpace() {
        return parkingSpace;
    }

    @JsonProperty("parkingSpace")
    public void setParkingSpace(String parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    @JsonProperty("parkingLot")
    public String getParkingLot() {
        return parkingLot;
    }

    @JsonProperty("parkingLot")
    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

    @JsonProperty("parkingLotId")
    public String getParkingLotId() {
        return parkingLotId;
    }

    @JsonProperty("parkingLotId")
    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    @JsonProperty("network")
    public String getNetwork() {
        return network;
    }

    @JsonProperty("network")
    public void setNetwork(String network) {
        this.network = network;
    }

    @JsonProperty("lat")
    public Double getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(Double lat) {
        this.lat = lat;
    }

    @JsonProperty("lon")
    public Double getLon() {
        return lon;
    }

    @JsonProperty("lon")
    public void setLon(Double lon) {
        this.lon = lon;
    }

    @JsonProperty("mode")
    public String getMode() {
        return mode;
    }

    @JsonProperty("mode")
    public void setMode(String mode) {
        this.mode = mode;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("carPresence")
    public Integer getCarPresence() {
        return carPresence;
    }

    @JsonProperty("carPresence")
    public void setCarPresence(Integer carPresence) {
        this.carPresence = carPresence;
    }

    @JsonProperty("carCounter")
    public Integer getCarCounter() {
        return carCounter;
    }

    @JsonProperty("carCounter")
    public void setCarCounter(Integer carCounter) {
        this.carCounter = carCounter;
    }

    @JsonProperty("serverTime")
    public String getServerTime() {
        return serverTime;
    }

    @JsonProperty("serverTime")
    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    @JsonProperty("gatewayTime")
    public String getGatewayTime() {
        return gatewayTime;
    }

    @JsonProperty("gatewayTime")
    public void setGatewayTime(String gatewayTime) {
        this.gatewayTime = gatewayTime;
    }

    @JsonProperty("sentralTime")
    public Integer getSentralTime() {
        return sentralTime;
    }

    @JsonProperty("sentralTime")
    public void setSentralTime(Integer sentralTime) {
        this.sentralTime = sentralTime;
    }

    @JsonProperty("rssi")
    public Integer getRssi() {
        return rssi;
    }

    @JsonProperty("rssi")
    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    @JsonProperty("snr")
    public Integer getSnr() {
        return snr;
    }

    @JsonProperty("snr")
    public void setSnr(Integer snr) {
        this.snr = snr;
    }

    @JsonProperty("temperature")
    public Double getTemperature() {
        return temperature;
    }

    @JsonProperty("temperature")
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @JsonProperty("battery")
    public Integer getBattery() {
        return battery;
    }

    @JsonProperty("battery")
    public void setBattery(Integer battery) {
        this.battery = battery;
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
        if (!(o instanceof ParkingSensor)) return false;
        ParkingSensor that = (ParkingSensor) o;
        return Objects.equals(sensorId, that.sensorId) &&
                Objects.equals(parkingSpace, that.parkingSpace) &&
                Objects.equals(parkingLot, that.parkingLot) &&
                Objects.equals(parkingLotId, that.parkingLotId) &&
                Objects.equals(network, that.network) &&
                Objects.equals(lat, that.lat) &&
                Objects.equals(lon, that.lon) &&
                Objects.equals(mode, that.mode) &&
                Objects.equals(status, that.status) &&
                Objects.equals(carPresence, that.carPresence) &&
                Objects.equals(carCounter, that.carCounter) &&
                Objects.equals(serverTime, that.serverTime) &&
                Objects.equals(gatewayTime, that.gatewayTime) &&
                Objects.equals(sentralTime, that.sentralTime) &&
                Objects.equals(rssi, that.rssi) &&
                Objects.equals(snr, that.snr) &&
                Objects.equals(temperature, that.temperature) &&
                Objects.equals(battery, that.battery);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sensorId, parkingSpace, parkingLot, parkingLotId,
                network, lat, lon, mode, status, carPresence, carCounter, serverTime,
                gatewayTime, sentralTime, rssi, snr, temperature, battery);
    }
}