package com.hits.vicinity.adapter.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "parking_lot")
public class ParkingGatewayObject extends AbstractTimestampEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "oid", updatable = false, nullable = false)
    private UUID oid;

    @Column(name = "gateway_mac")
    private String gatewayMac;

    @Column(name = "gateway_name")
    private String gatewayName;

    @Column(name = "lot_id")
    private String parkingLotId;
}
