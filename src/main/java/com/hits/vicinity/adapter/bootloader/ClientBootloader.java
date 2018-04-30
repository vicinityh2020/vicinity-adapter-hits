package com.hits.vicinity.adapter.bootloader;

import com.hits.vicinity.adapter.api.pni.PniClient;
import com.hits.vicinity.adapter.domain.pni.ParkingLot;
import com.hits.vicinity.adapter.domain.pni.ParkingSensor;
import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyMap;

@Component
public class ClientBootloader implements ApplicationListener<ContextRefreshedEvent> {

    private PniClient placePodClient;

    public ClientBootloader(PniClient placePodClient) {
        this.placePodClient = placePodClient;
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

        System.out.println("Test done");
    }
}
