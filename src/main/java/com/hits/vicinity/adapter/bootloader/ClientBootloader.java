package com.hits.vicinity.adapter.bootloader;

import com.hits.vicinity.adapter.api.PNIPlacePodClient;
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

    private PNIPlacePodClient placePodClient;

    public ClientBootloader(PNIPlacePodClient placePodClient) {
        this.placePodClient = placePodClient;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Logger.setLevel(Logger.DEBUG);
        List<ParkingSensor> s = placePodClient.postSensors(emptyMap());
        List<ParkingLot> l = placePodClient.getParkingLots();

        System.out.println(l.get(0).getStreetAddress());
    }

}
