package com.hits.vicinity.adapter.controller;

import com.hits.vicinity.adapter.domain.vicinity.ObjectsJson;
import com.hits.vicinity.adapter.repository.ParkingLotRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VicinityApiController {

    private ParkingLotRepository parkingLotRepository;

    @GetMapping(value = "/objects", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ObjectsJson> getObjects() {
        return null;
    }

    @GetMapping(value = "/device/{oid}/property/{pid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ObjectsJson> getProperty() {
        return null;
    }

}
