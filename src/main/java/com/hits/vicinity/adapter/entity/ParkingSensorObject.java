package com.hits.vicinity.adapter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity

@Table(name = "sensors")
@JsonIgnoreProperties(value = {"created_at", "last_modified"}, allowGetters = true)

public class ParkingSensorObject extends AbstractTimestampEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "oid", updatable = false, nullable = false)
    private UUID oid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lot_oid")
    private ParkingLotObject lot;

    @Column(name = "sensor_id", nullable = false, unique = true)
    private String sensorId;

    @Column(name = "network", nullable = false)
    private String network;

    @Column(name = "parking_space_id")
    private String parkingSpaceId;

    @Column(name = "parking_lot_name")
    private String parkingLotName;

    @Column(name = "status")
    private String status;

    @Column(name = "car_presence")
    private Integer carPresence;

    @Column(name = "car_counter")
    private Integer carCounter;

    public String getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(String parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCarPresence() {
        return carPresence;
    }

    public void setCarPresence(Integer carPresence) {
        this.carPresence = carPresence;
    }

    public Integer getCarCounter() {
        return carCounter;
    }

    public void setCarCounter(Integer carCounter) {
        this.carCounter = carCounter;
    }

    public UUID getOid() {
        return oid;
    }

    public ParkingLotObject getLot() {
        return lot;
    }

    public void setLot(ParkingLotObject lot) {
        this.lot = lot;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }
}
