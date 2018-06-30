package com.hits.vicinity.adapter.api.pni;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hits.vicinity.adapter.domain.pni.ResponseSuccess;
import com.hits.vicinity.adapter.domain.pni.ParkingLot;
import com.hits.vicinity.adapter.domain.pni.ParkingSensor;
import com.sun.media.jfxmedia.logging.Logger;
import jdk.nashorn.internal.ir.ObjectNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Service
public class PniClient {

    private final HttpEntity defaultRequest;
    private RestTemplate restTemplate;
    private HttpHeaders defaultHeaders;

    private static final String PARKING_LOT = "parking-lot";
    private static final String PARKING_SENSOR = "parking-sensor";
    private static final String PARKING_GATEWAY = "parking-gateway";

    @Value("${pnicloud.client.baseurl}")
    private String baseUri;

    @Value("${pnicloud.client.apiversion}")
    private String apiVersion;

    public PniClient(RestTemplate restTemplate, @Value("${pnicloud.client.apikey}") String apiKey) {
        this.restTemplate = restTemplate;
        this.defaultHeaders = new HttpHeaders();

        defaultHeaders.setAccept(singletonList(MediaType.APPLICATION_JSON));
        defaultHeaders.setContentType(MediaType.APPLICATION_JSON);
        defaultHeaders.set("X-API-KEY", apiKey);

        defaultRequest = new HttpEntity(defaultHeaders);
    }

    /**
     * Retrieves a list of all parking lots
     *
     * @return A list of ParkingLot items
     * @see ParkingLot
     */
    public List<ParkingLot> getParkingLots() {
        final String methodName = new Throwable().getStackTrace()[0].getMethodName();
        final String uri = format("https://%s/%s/%s", baseUri, apiVersion, "parking-lots");

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

    public List<ParkingSensor> getSensors(ObjectNode filters) {

        final String methodName = new Throwable().getStackTrace()[0].getMethodName();
        final String uri = format("https://%s/%s/%s", baseUri, apiVersion, "sensors");

        try {

            Logger.logMsg(Logger.DEBUG, this.getClass().getName(), methodName, format("Requesting %s", uri));

            long startTime = System.nanoTime();
            ResponseEntity<ParkingSensor[]> response;
            if (filters != null) {
                response = restTemplate.exchange(uri, HttpMethod.POST, defaultRequest, ParkingSensor[].class, filters.toString());
            } else {
                response = restTemplate.exchange(uri, HttpMethod.POST, defaultRequest, ParkingSensor[].class);
            }
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

    private boolean performCrudAction(String modelName, HttpMethod method, JSONObject filters) {
        final String methodName = new Throwable().getStackTrace()[0].getMethodName();
        final String actionEndpoint;

        switch (method) {
            case PUT:
                actionEndpoint = "update";
                break;
            case POST:
                actionEndpoint = "insert";
                break;
            case DELETE:
                actionEndpoint = "remove";
                break;
            default:
                Logger.logMsg(Logger.ERROR, this.getClass().getName(), methodName,
                        format("Method \"%s\" is not supported on the model \"%s\".",
                                method.toString(), modelName));
                return false;
        }

        final String uri = format("https://%s/%s/%s/%s", baseUri, apiVersion, modelName, actionEndpoint);

        HttpEntity<String> request = new HttpEntity<>(filters.toString(), defaultHeaders);
        try {
            ResponseEntity<ResponseSuccess> response = restTemplate
                    .exchange(uri, method, request, ResponseSuccess.class);

            if (isSuccess(methodName, response.getStatusCode(), response.hasBody())) {
                return true;
            }
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
        } catch (RestClientException e) {
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
                        methodName, "No body in response.");
            }
        } else {
            Logger.logMsg(Logger.ERROR, this.getClass().getName(),
                    methodName, String.format("Status code: %d (%s).", status.value(), status.getReasonPhrase()));
        }
        return false;
    }

    public boolean updateParkingLot(JSONObject updateBy) {
        return performCrudAction(PARKING_LOT, HttpMethod.PUT, updateBy);
    }

    public boolean insertParkingLot(JSONObject insertBy) {
        return performCrudAction(PARKING_LOT, HttpMethod.POST, insertBy);
    }

    public boolean removeParkingLot(JSONObject removeBy) {
        return performCrudAction(PARKING_LOT, HttpMethod.DELETE, removeBy);
    }

    public boolean updateParkingSensor(JSONObject updateBy) {
        return performCrudAction(PARKING_SENSOR, HttpMethod.PUT, updateBy);
    }

    public boolean insertParkingSensor(JSONObject insertBy) {
        return performCrudAction(PARKING_SENSOR, HttpMethod.POST, insertBy);
    }

    public boolean removeParkingSensor(JSONObject removeBy) {
        return performCrudAction(PARKING_SENSOR, HttpMethod.DELETE, removeBy);
    }

    public boolean updateParkingGateway(JSONObject updateBy) {
        return performCrudAction(PARKING_GATEWAY, HttpMethod.PUT, updateBy);
    }

    public boolean insertParkingGateway(JSONObject insertBy) {
        return performCrudAction(PARKING_GATEWAY, HttpMethod.POST, insertBy);
    }

    public boolean removeParkingGateway(JSONObject removeBy) {
        return performCrudAction(PARKING_GATEWAY, HttpMethod.DELETE, removeBy);
    }

    public static JSONObject byId(String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


}
