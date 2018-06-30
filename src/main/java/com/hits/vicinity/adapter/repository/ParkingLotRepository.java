package com.hits.vicinity.adapter.repository;

import com.hits.vicinity.adapter.entity.ParkingLotObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLotObject, UUID> {

    @Query("SELECT l from ParkingLotObject l WHERE l.lotId = :id")
    Optional<ParkingLotObject> findByLotId(@Param("id") String id);

}
