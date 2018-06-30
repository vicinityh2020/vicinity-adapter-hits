package com.hits.vicinity.adapter.repository;

import com.hits.vicinity.adapter.domain.pni.ParkingSensor;
import com.hits.vicinity.adapter.entity.ParkingSensorObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParkingSensorRepository extends JpaRepository<ParkingSensorObject, UUID> {
    List<ParkingSensorObject> findByLotOid(UUID oid);
    Optional<ParkingSensorObject> findBySensorId(String id);
}

