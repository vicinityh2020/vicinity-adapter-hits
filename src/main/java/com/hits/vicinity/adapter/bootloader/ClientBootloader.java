package com.hits.vicinity.adapter.bootloader;

import com.hits.vicinity.adapter.api.pni.PniClient;
import com.hits.vicinity.adapter.domain.pni.ParkingGateway;
import com.hits.vicinity.adapter.domain.pni.ParkingLot;
import com.hits.vicinity.adapter.entity.ParkingLotObject;
import com.hits.vicinity.adapter.repository.ParkingLotRepository;
import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

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
        List<ParkingLot> syncedLots = syncParkingLots();

        System.out.println("Test done");
    }

    private List<ParkingLot> syncParkingLots() {
        List<ParkingLot> lots = placePodClient.getParkingLots();

        lots.forEach(parkingLot -> {
            ParkingLotObject parkingLotObject = new ParkingLotObject();

            parkingLotObject.setLotId(parkingLot.getId());
            parkingLotObject.setLotName(parkingLot.getName());
            parkingLotObject.setStreetAddress(parkingLot.getStreetAddress());
            parkingLotObject.setLatitude(parkingLot.getLatitude());
            parkingLotObject.setLongitude(parkingLot.getLongitude());
            parkingLotObject.setDescription(parkingLot.getDescription());
            parkingLotObject.setCameraId(parkingLot.getCameraId());

            parkingLotRepository.save(parkingLotObject);

        });

        return lots;
    }

    private void syncParkingGateways() {
        List<ParkingGateway> gateways;
    }

    private void syncParkingSensors(List<ParkingLot> filter) {

    }
}
