package com.hits.vicinity.adapter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "lot_id")
    private ParkingLotObject lot;

    @Column(name = "sensor_id", nullable = false, unique = true)
    private String sensorId;

    @Column(name = "network", nullable = false)
    private String network;

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
