package com.hits.vicinity.adapter.api;

import com.hits.vicinity.adapter.domain.pni.ParkingLot;
import com.hits.vicinity.adapter.domain.pni.ParkingSensor;
import com.sun.media.jfxmedia.logging.Logger;
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
        final String methodName = new Throwable().getStackTrace()[0].getMethodName();
        final String uri = format("https://%s/%s/%s", baseURI, apiVersion, "parking-lots");

        HttpEntity request = new HttpEntity(defaultHeaders);

        try {
            Logger.logMsg(Logger.DEBUG, this.getClass().getName(), methodName, "Request ...");
            long startTime = System.nanoTime();
            ResponseEntity<ParkingLot[]> response =
                    restTemplate.exchange(uri, HttpMethod.GET, request, ParkingLot[].class);
            long endTime = System.nanoTime();

            Logger.logMsg(Logger.DEBUG, this.getClass().getName(), methodName, String.format("Response! %d ms", (endTime - startTime) / 1000000));

            HttpStatus status = response.getStatusCode();
            if (status == HttpStatus.OK) {
                if (response.hasBody()) {
                    return Arrays.asList(response.getBody());
                } else {
                    Logger.logMsg(Logger.ERROR, this.getClass().getName(),
                            methodName, "No body in response");
                }
            } else {
                Logger.logMsg(Logger.ERROR, this.getClass().getName(),
                        methodName, String.format("Status code: %d (%s)", status.value(), status.getReasonPhrase()));
            }
        } catch (RestClientException e) {
            Logger.logMsg(Logger.ERROR, this.getClass().getName(), methodName, e.getMessage());
            e.printStackTrace();
        }
        return emptyList();
    }

    public List<ParkingSensor> getSensors() {
        return emptyList();
    }

}
