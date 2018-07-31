package com.hits.vicinity.adapter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hits.vicinity.adapter.entity.ParkingSensorObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class EventService {
    private RestTemplate restTemplate;
    private ObjectMapper mapper;

    @Value("${adapter.id}")
    private String adapterId;

    @Value("${agent.baseurl}")
    private String agentBaseUrl;

    @Value("${agent.port}")
    private String agentPort;

    @Value("${agent.scheme}")
    private String agentScheme;

    public EventService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.mapper = objectMapper;
    }

    public void publishEvent(ParkingSensorObject device) {
        String infrastructureId = device.getOid().toString();
        HttpHeaders eventHeaders = new HttpHeaders();

        eventHeaders.setContentType(MediaType.APPLICATION_JSON);
        eventHeaders.set("infrastructure-id", infrastructureId);
        eventHeaders.set("adapter-id", this.adapterId);

        ObjectNode eventBody = mapper.createObjectNode();
        eventBody.put("sensor_id", device.getSensorId());
        eventBody.put("value", device.getStatus());
        eventBody.put("time", device.getLastModifiedDate().toString());

        String agentEventUrl = UriComponentsBuilder
                .fromHttpUrl(String.format("%s://%s:%s", this.agentScheme, this.agentBaseUrl, this.agentPort))
                .path(String.format("/agent/events/sensor-%s", infrastructureId))
                .toUriString();

        HttpEntity<ObjectNode> eventRequest = new HttpEntity<>(eventBody, eventHeaders);

        try {
            ResponseEntity<JsonNode> response = restTemplate.exchange(agentEventUrl, HttpMethod.PUT, eventRequest, JsonNode.class);
            if (!response.getStatusCode().is5xxServerError()) {
                System.out.println(response.getBody().toString());
            } else {
                System.out.println("Event could not be published. 500.");
            }
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
        }
    }
}
