package com.hits.vicinity.adapter.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hits.vicinity.adapter.domain.pni.ParkingSensor;
import com.hits.vicinity.adapter.domain.vicinity.*;
import com.hits.vicinity.adapter.entity.ParkingSensorObject;
import com.hits.vicinity.adapter.repository.ParkingLotRepository;
import com.hits.vicinity.adapter.repository.ParkingSensorRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static java.util.Collections.*;

@RestController
public class VicinityApiController {

    private ParkingLotRepository parkingLotRepository;
    private ParkingSensorRepository parkingSensorRepository;
    private ObjectMapper mapper;

    private static final String statusPropertyEndpoint = "/device/{oid}/property/{pid}";

    public VicinityApiController(ParkingLotRepository parkingLotRepository,
                                 ParkingSensorRepository parkingSensorRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingSensorRepository = parkingSensorRepository;
        this.mapper = new ObjectMapper();
    }

    private ObjectNode createField(String name, String type) {
        return JsonNodeFactory.instance.objectNode()
                .put("name", name)
                .putPOJO("schema", mapper.valueToTree(singletonMap("type", type)));
    }

    @GetMapping(value = "/objects", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ObjectsJson> getObjects() {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.NOT_FOUND;

        ObjectsJson responseTemplate = new ObjectsJson();

        List<ObjectNode> outputFields = new ArrayList<>();
        outputFields.add(createField("property", "string"));
        outputFields.add(createField("value", "integer"));

        Output readLinkOutput = new Output();
        readLinkOutput.setType("object");
        readLinkOutput.setField(mapper.valueToTree(outputFields));

        ReadLink readLink = new ReadLink();
        readLink.setHref(statusPropertyEndpoint);
        readLink.setOutput(readLinkOutput);

        Output writeLinkInput = new Output();
        writeLinkInput.setType("object");
        writeLinkInput.setField(mapper.valueToTree(singletonList(createField("vacancy", "integer"))));

        Output writeLinkOutput = new Output();
        writeLinkOutput.setType("object");
        writeLinkOutput.setField(mapper.valueToTree(singletonList(createField("success", "boolean"))));

        WriteLink writeLink = new WriteLink();
        writeLink.setHref(statusPropertyEndpoint);
        writeLink.setInput(writeLinkInput);
        writeLink.setOutput(writeLinkOutput);

        Property property = new Property();
        property.setPid("status");
        property.setMonitors("Parking space vacancy");
        property.setReadLink(readLink);
        property.setWriteLink(writeLink);

        List<ParkingSensorObject> sensors = parkingSensorRepository.findAll();

        if (sensors.size() > 0) {

            status = HttpStatus.OK;

            // TODO: for now we only have 1 property and 1 object
            // TODO: make it so that more properties/objects can be added to the responseTemplate
            for (ParkingSensorObject sensor : sensors) {
                responseTemplate.setOid(sensor.getOid().toString());
                // TODO: TEMP, use real name/type later
                responseTemplate.setName("sensor.getName");
                responseTemplate.setType("sensor.getType");

                responseTemplate.setProperties(singletonList(property));

                responseTemplate.setActions(emptyList());
                responseTemplate.setEvents(emptyList());
            }
        }

        // TODO: add last modified header
        return new ResponseEntity<>(responseTemplate, headers, status);
    }

    @GetMapping(value = statusPropertyEndpoint, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ObjectsJson> getProperty() {
        return null;
    }

    @PutMapping(value = statusPropertyEndpoint, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ObjectsJson> updateProperty() {
        return null;
    }
}
