package com.hits.vicinity.adapter.bootloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hits.vicinity.adapter.api.pni.PniClient;
import com.hits.vicinity.adapter.domain.pni.ParkingLot;
import com.hits.vicinity.adapter.domain.pni.ParkingSensor;
import com.hits.vicinity.adapter.entity.ParkingLotObject;
import com.hits.vicinity.adapter.repository.ParkingLotRepository;
import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyMap;

@Component
public class ClientBootloader implements ApplicationListener<ContextRefreshedEvent> {

    private PniClient placePodClient;
    private ParkingLotRepository parkingLotRepository;

    public ClientBootloader(PniClient placePodClient, ParkingLotRepository parkingLotRepository) {
        this.placePodClient = placePodClient;
        this.parkingLotRepository = parkingLotRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Logger.setLevel(Logger.DEBUG);
        List<ParkingSensor> s = placePodClient.getSensors(emptyMap());
        List<ParkingLot> l = placePodClient.getParkingLots();

        System.out.println(l.get(0).getStreetAddress());

        if (placePodClient.removeParkingLot(PniClient.byId("5ae4dd390bb8980001692aba"))) {
            System.out.println("Removed!");
        }

        List<ParkingLotObject> lots = new ArrayList<>(l.size());

        for (int i = 0; i < l.size(); i++) {
            ParkingLot other = l.get(i);
            ParkingLotObject parkingLotObject = new ParkingLotObject();

            parkingLotObject.setPniId(other.getId());
            parkingLotObject.setLotName(other.getName());
            parkingLotObject.setStreetAddress(other.getStreetAddress());
            parkingLotObject.setLatitude(other.getLatitude());
            parkingLotObject.setLatitude(other.getLongitude());
            parkingLotObject.setDescription(other.getDescription());
            parkingLotObject.setCameraId(other.getCameraId());

            lots.add(parkingLotObject);
        }

        parkingLotRepository.saveAll(lots);
        //parkingLotRepository.saveAll(l);

        System.out.println("Test done");
    }
}
