package com.hits.vicinity.adapter.api.pni;

import com.hits.vicinity.adapter.domain.pni.ParkingSensor;
import com.hits.vicinity.adapter.entity.ParkingLotObject;
import com.hits.vicinity.adapter.entity.ParkingSensorObject;
import com.hits.vicinity.adapter.repository.ParkingLotRepository;
import com.hits.vicinity.adapter.repository.ParkingSensorRepository;
import com.hits.vicinity.adapter.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Component
public class ScheduledPolling {

    private static final Logger log = LoggerFactory.getLogger(ScheduledPolling.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    ParkingSensorRepository parkingSensorRepository;
    ParkingLotRepository parkingLotRepository;
    EventService eventService;

    private PniClient pniClient;

    public ScheduledPolling(PniClient pniClient,
                            ParkingSensorRepository parkingSensorRepository,
                            ParkingLotRepository parkingLotRepository, EventService eventService) {
        this.pniClient = pniClient;
        this.parkingSensorRepository = parkingSensorRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.eventService = eventService;
    }

    @Scheduled(fixedRate = 60000)
    public void pollSensors() {

        /* ************************************************** */
        /* TODO: Replace this with MQTT and drop the database */
        /* ************************************************** */

        List<ParkingLotObject> parkingLotObjectList = parkingLotRepository.findAll();

        if (parkingLotObjectList.size() == 0) {
            return;
        }

        List<ParkingSensor> parkingSensors = this.pniClient.getSensors(null);
        parkingSensorRepository.findBySensorId("");

        for (ParkingSensor sensor : parkingSensors) {

            ParkingSensorObject other = new ParkingSensorObject();

            other.setSensorId(sensor.getSensorId());
            other.setNetwork(sensor.getNetwork());
            other.setLot(parkingLotObjectList.get(0));
            other.setCarCounter(sensor.getCarCounter());
            other.setParkingLotName(sensor.getParkingLot());
            other.setParkingSpaceId(sensor.getParkingSpace());
            other.setStatus(sensor.getStatus());
            other.setCarPresence(sensor.getCarPresence());

            Optional<ParkingSensorObject> res = parkingSensorRepository.findBySensorId(sensor.getSensorId());
            ParkingSensorObject parkingSensorObject = res.orElse(other);

            parkingSensorObject.setStatus(sensor.getStatus());

            parkingSensorRepository.save(parkingSensorObject);

            eventService.publishEvent(parkingSensorObject);
        }
    }

    public static int translateStatusToInt(String status) {
        int val = 0;
        switch (status.toLowerCase()) {
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
        return val;
    }
}
