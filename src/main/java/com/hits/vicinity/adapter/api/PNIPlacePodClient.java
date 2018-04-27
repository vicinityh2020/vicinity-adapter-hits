package com.hits.vicinity.adapter.api;

import com.hits.vicinity.adapter.domain.pni.ParkingLot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.lang.String.format;

@Service
public class PNIPlacePodClient {

    private RestTemplate restTemplate;

    private HttpHeaders defaultHeaders;

    @Value("${pnicloud.client.baseurl}")
    private String baseURI;

    @Value("${pnicloud.client.apiversion}")
    private String apiVersion;

    public PNIPlacePodClient(RestTemplate restTemplate, @Value("${pnicloud.client.apikey}") String apiKey) {
        this.restTemplate = restTemplate;
        this.defaultHeaders = new HttpHeaders();

        defaultHeaders.setAccept(singletonList(MediaType.APPLICATION_JSON));
        defaultHeaders.setContentType(MediaType.APPLICATION_JSON);
        defaultHeaders.set("X-API-KEY", apiKey);
    }

    /**
     * Retrieves a list of all parking lots
     *
     * @return A list of ParkingLot items
     * @see ParkingLot
     */
    public List<ParkingLot> getParkingLots() {

        final String uri = format("https://%s/%s/%s", baseURI, apiVersion, "parking-lots");

        HttpEntity requestEntity = new HttpEntity(defaultHeaders);

        try {
            ResponseEntity<ParkingLot[]> responseEntity =
                    restTemplate.exchange(uri, HttpMethod.GET, requestEntity, ParkingLot[].class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                if (requestEntity.hasBody()) {
                    return Arrays.asList(responseEntity.getBody());
                } else {
                    return emptyList();
                }
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

}
