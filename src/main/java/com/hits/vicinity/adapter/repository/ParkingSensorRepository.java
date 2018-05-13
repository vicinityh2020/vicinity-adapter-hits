package com.hits.vicinity.adapter.repository;

import com.hits.vicinity.adapter.entity.ParkingSensorObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParkingSensorRepository extends JpaRepository<ParkingSensorObject, UUID> {

}
