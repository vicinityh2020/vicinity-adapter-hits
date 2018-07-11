package com.hits.vicinity.adapter.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hits.vicinity.adapter.domain.vicinity.*;
import com.hits.vicinity.adapter.entity.ParkingSensorObject;
import com.hits.vicinity.adapter.repository.ParkingLotRepository;
import com.hits.vicinity.adapter.repository.ParkingSensorRepository;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.*;

@RestController
public class VicinityApiController {

    private ParkingLotRepository parkingLotRepository;
    private ParkingSensorRepository parkingSensorRepository;
    private ObjectMapper mapper;
    private PropertySchema availabilityProperty;

    private static final String statusPropertyEndpoint = "/devices/{oid}/properties/{pid}";

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

    private JsonNode prepareEvent(ParkingSensorObject device) {

        ArrayNode fields = mapper.createArrayNode();
        fields.add(createField("value", "integer"));
        fields.add(createField("time", "string"));

        OutputInputSchema output = new OutputInputSchema();
        output.setAdditionalProperty("description", "state describing if sensor is occupied or not.");
        output.setType("object");
        output.setField(fields);

        ObjectNode eventDescription = mapper.createObjectNode();
        eventDescription.put("eid", "sensor-" + device.getOid());
        eventDescription.put("monitors", "adapters:DeviceStatus");
        eventDescription.putPOJO("output", output);

        return eventDescription;
    }

    private void prepareObjects(String endpoint) {

        List<ObjectNode> outputFields = new ArrayList<>();
        outputFields.add(createField("property", "string"));
        outputFields.add(createField("value", "integer"));

        OutputInputSchema readLinkOutput = new OutputInputSchema();
        readLinkOutput.setType("object");
        readLinkOutput.setField(mapper.valueToTree(outputFields));

        ReadLink readLink = new ReadLink();
        readLink.setHref(endpoint);
        readLink.setOutput(readLinkOutput);

        OutputInputSchema writeLinkInput = new OutputInputSchema();
        writeLinkInput.setType("object");
        writeLinkInput.setField(mapper.valueToTree(singletonList(createField("value", "integer"))));

        OutputInputSchema writeLinkOutput = new OutputInputSchema();
        writeLinkOutput.setType("object");
        writeLinkOutput.setField(mapper.valueToTree(singletonList(createField("success", "boolean"))));

        WriteLink writeLink = new WriteLink();
        writeLink.setHref(endpoint);
        writeLink.setInput(writeLinkInput);
        writeLink.setOutput(writeLinkOutput);

        PropertySchema property = new PropertySchema();
        property.setPid("status");
        property.setMonitors("adapters:DeviceStatus");
        property.setReadLink(readLink);
        property.setWriteLink(writeLink);

        this.availabilityProperty = property;
    }

    @GetMapping(value = "/objects", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ThingDescriptor> getObjects() {

        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.NOT_FOUND;

        List<ParkingSensorObject> sensors = parkingSensorRepository.findAll();
        List<ObjectsJson> exposedDevices = new ArrayList<ObjectsJson>();

        if (sensors.size() > 0) {

            status = HttpStatus.OK;

            for (ParkingSensorObject sensor : sensors) {

                ObjectsJson responseTemplate = new ObjectsJson();

                responseTemplate.setOid(sensor.getOid().toString());
                responseTemplate.setName(sensor.getSensorId());
                responseTemplate.setType("core:Device");

                prepareObjects(String.format("/devices/%s/properties/%s", sensor.getOid().toString(), "status"));

                responseTemplate.setProperties(singletonList(availabilityProperty));

                responseTemplate.setActions(emptyList());
                responseTemplate.setEvents(emptyList());

                exposedDevices.add(responseTemplate);
                responseTemplate.setEvents(singletonList(prepareEvent(sensor)));
            }
        }

        ThingDescriptor thingDescriptor = new ThingDescriptor();

        thingDescriptor.setAdapterId("1");
        thingDescriptor.setThingDescriptions(exposedDevices);

        // TODO: add last modified header
        return new ResponseEntity<>(thingDescriptor, headers, status);
    }

    @GetMapping(value = statusPropertyEndpoint, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Property> getProperty(@PathVariable("oid") UUID oid, @PathVariable("pid") String pid) {

        Optional<ParkingSensorObject> optionalSensor = parkingSensorRepository.findById(oid);
        ParkingSensorObject sensor = optionalSensor.orElseThrow(() -> new NoResultException("Supplied oid does not exist"));

        if (!pid.equals("status")) {
            throw new NoResultException("Supplied property does not exist for the given oid");
        }

        int val = 0;
        switch (sensor.getStatus().toLowerCase()) {
            case "vacant":
                val = 1;
                break;
            case "occupied":
                val = 2;
                break;
            case "recalibrating":
                val = 3;
                break;
        }

        return new ResponseEntity<>(new Property(pid, val), HttpStatus.OK);

    }

    @PutMapping(value = statusPropertyEndpoint,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<JsonNode> updateProperty(
            @PathVariable("oid") UUID oid,
            @PathVariable("pid") String pid,
            @RequestBody ObjectNode o) {

        Optional<ParkingSensorObject> optionalSensor = parkingSensorRepository.findById(oid);
        ParkingSensorObject sensor = optionalSensor.orElseThrow(() -> new NoResultException("Supplied oid does not exist"));

        if (!pid.equals("status")) {
            throw new NoResultException("Supplied property does not exist for the given oid");
        }

        JsonNode statusNode = o.get("value");

        // TODO: sensor.setStatus(status)

        if (statusNode.canConvertToInt()) {
            return new ResponseEntity<>(JsonNodeFactory.instance.objectNode().put("success", true), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
