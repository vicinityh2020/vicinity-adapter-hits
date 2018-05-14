package com.hits.vicinity.adapter.controller;

import com.hits.vicinity.adapter.domain.pni.ParkingSensor;
import com.hits.vicinity.adapter.domain.vicinity.ObjectsJson;
import com.hits.vicinity.adapter.entity.ParkingSensorObject;
import com.hits.vicinity.adapter.repository.ParkingLotRepository;
import com.hits.vicinity.adapter.repository.ParkingSensorRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class VicinityApiController {

    private ParkingLotRepository parkingLotRepository;
    private ParkingSensorRepository parkingSensorRepository;

    public VicinityApiController(ParkingLotRepository parkingLotRepository,
                                 ParkingSensorRepository parkingSensorRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingSensorRepository = parkingSensorRepository;
    }

    @GetMapping(value = "/objects", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ObjectsJson> getObjects() {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.NOT_FOUND;

        List<ParkingSensorObject> sensors = parkingSensorRepository.findAll();
        ObjectsJson responseTemplate = new ObjectsJson();

        for (ParkingSensorObject sensor : sensors) {
            responseTemplate.setOid(sensor.getOid().toString());
            responseTemplate.setActions(Collections.emptyList());
            responseTemplate.setEvents(Collections.emptyList());
            // TODO: TEMP
            responseTemplate.setName("sensor.getName");
            responseTemplate.setType("sensor.getType");
        }

        return null;

    }

    @GetMapping(value = "/device/{oid}/property/{pid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ObjectsJson> getProperty() {
        return null;
    }
}
