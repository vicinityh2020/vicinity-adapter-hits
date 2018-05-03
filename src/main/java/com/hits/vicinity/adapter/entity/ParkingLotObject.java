package com.hits.vicinity.adapter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "parking_lot")
@JsonIgnoreProperties(value = {"created_at", "last_modified"}, allowGetters = true)
public class ParkingLotObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "pni_id", unique = true)
    private String pniId;

    @Column(name = "lot_name")
    private String lotName;

    @Column(name = "description")
    private String description;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "cameraId")
    private String cameraId;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private java.util.Date createdAtDate;

    @Column(name = "last_modified", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private java.util.Date lastModifiedDate;

    public long getId() {
        return id;
    }

    public String getPniId() {
        return pniId;
    }

    public void setPniId(String pniId) {
        this.pniId = pniId;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
