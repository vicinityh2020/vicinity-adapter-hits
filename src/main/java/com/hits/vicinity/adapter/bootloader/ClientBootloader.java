package com.hits.vicinity.adapter.bootloader;

import com.hits.vicinity.adapter.api.pni.PniClient;
import com.hits.vicinity.adapter.domain.pni.ParkingGateway;
import com.hits.vicinity.adapter.domain.pni.ParkingLot;
import com.hits.vicinity.adapter.domain.pni.ParkingSensor;
import com.hits.vicinity.adapter.entity.ParkingLotObject;
import com.hits.vicinity.adapter.entity.ParkingSensorObject;
import com.hits.vicinity.adapter.repository.ParkingLotRepository;
import com.hits.vicinity.adapter.repository.ParkingSensorRepository;
import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientBootloader implements ApplicationListener<ContextRefreshedEvent> {

    private PniClient placePodClient;
    private ParkingLotRepository parkingLotRepository;
    private ParkingSensorRepository parkingSensorRepository;

    public ClientBootloader(PniClient placePodClient,
                            ParkingLotRepository parkingLotRepository,
                            ParkingSensorRepository parkingSensorRepository) {

        this.placePodClient = placePodClient;
        this.parkingLotRepository = parkingLotRepository;
        this.parkingSensorRepository = parkingSensorRepository;
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

            ParkingLotObject other = new ParkingLotObject();

            other.setLotId(parkingLot.getId());
            other.setLotName(parkingLot.getName());
            other.setStreetAddress(parkingLot.getStreetAddress());
            other.setLatitude(parkingLot.getLatitude());
            other.setLongitude(parkingLot.getLongitude());
            other.setDescription(parkingLot.getDescription());
            other.setCameraId(parkingLot.getCameraId());

            ParkingLotObject parkingLotObject = this.parkingLotRepository.findByLotId(parkingLot.getId()).orElse(other);

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
