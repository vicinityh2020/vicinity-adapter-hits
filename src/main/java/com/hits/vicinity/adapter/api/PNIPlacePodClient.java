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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Service
public class PNIPlacePodClient {

    private RestTemplate restTemplate;

    private HttpHeaders defaultHeaders;

    private HttpEntity defaultRequest;

    private Map<HttpMethod, String> crudActions;

    @Value("${pnicloud.client.baseurl}")
    private String baseURI;

    @Value("${pnicloud.client.apiversion}")
    private String apiVersion;

    public PNIPlacePodClient(RestTemplate restTemplate, @Value("${pnicloud.client.apikey}") String apiKey) {
        this.restTemplate = restTemplate;
        this.defaultHeaders = new HttpHeaders();
        this.crudActions = new HashMap<>(3);

        defaultHeaders.setAccept(singletonList(MediaType.APPLICATION_JSON));
        defaultHeaders.setContentType(MediaType.APPLICATION_JSON);
        defaultHeaders.set("X-API-KEY", apiKey);

        defaultRequest = new HttpEntity(defaultHeaders);

        crudActions.put(HttpMethod.POST, "insert");
        crudActions.put(HttpMethod.PUT, "update");
        crudActions.put(HttpMethod.DELETE, "delete");
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

        try {
            Logger.logMsg(Logger.DEBUG, this.getClass().getName(), methodName, format("Request %s", uri));
            long startTime = System.nanoTime();
            ResponseEntity<ParkingLot[]> response =
                    restTemplate.exchange(uri, HttpMethod.GET, defaultRequest, ParkingLot[].class);
            long endTime = System.nanoTime();

            Logger.logMsg(Logger.DEBUG, this.getClass().getName(), methodName, format("Response! %d ms", (endTime - startTime) / 1000000));

            if (isSuccess(methodName, response.getStatusCode(), response.hasBody())) {
                return Arrays.asList(response.getBody());
            }
        } catch (RestClientException e) {
            Logger.logMsg(Logger.ERROR, this.getClass().getName(), methodName, e.getMessage());
            e.printStackTrace();
        }
        return emptyList();
    }

    public List<ParkingSensor> postSensors(Map<String, ?> filters) {

        final String methodName = new Throwable().getStackTrace()[0].getMethodName();
        final String uri = format("https://%s/%s/%s", baseURI, apiVersion, "sensors");

        try {

            Logger.logMsg(Logger.DEBUG, this.getClass().getName(), methodName, format("Requesting %s", uri));

            long startTime = System.nanoTime();
            ResponseEntity<ParkingSensor[]> response =
                    restTemplate.exchange(uri, HttpMethod.POST, defaultRequest, ParkingSensor[].class, filters);
            long endTime = System.nanoTime();

            Logger.logMsg(Logger.DEBUG, this.getClass().getName(), methodName, String.format("Response received; %d ms", (endTime - startTime) / 1000000));

            if (isSuccess(methodName, response.getStatusCode(), response.hasBody())) {
                return Arrays.asList(response.getBody());
            }

        } catch (RestClientException e) {
            Logger.logMsg(Logger.ERROR, this.getClass().getName(), methodName, e.getMessage());
            e.printStackTrace();
        }

        return emptyList();
    }

    private boolean performCrudAction(String domainName, HttpMethod method, Map<String, ?> filters) {
        final String methodName = new Throwable().getStackTrace()[0].getMethodName();
        final String uri = format("https://%s/%s/%s/%s", baseURI, apiVersion, domainName, this.crudActions.get(method));

        try {
            ResponseEntity response = restTemplate.exchange(uri, method, defaultRequest, Object.class, filters);

            if (isSuccess(methodName, response.getStatusCode(), response.hasBody())) {
                return true;
            }

        } catch (RestClientException e) {
            Logger.logMsg(Logger.ERROR, this.getClass().getName(), methodName, e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private boolean isSuccess(String methodName, HttpStatus status, boolean hasBody) {
        if (status.is2xxSuccessful()) {
            if (hasBody) {
                return true;
            } else {
                Logger.logMsg(Logger.ERROR, this.getClass().getName(),
                        methodName, "No body in response");
            }
        } else {
            Logger.logMsg(Logger.ERROR, this.getClass().getName(),
                    methodName, String.format("Status code: %d (%s)", status.value(), status.getReasonPhrase()));
        }
        return false;
    }

}
