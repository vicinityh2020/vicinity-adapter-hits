package com.hits.vicinity.adapter.repository;

import com.hits.vicinity.adapter.entity.ParkingLotObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLotObject, String> {
}
