package com.hits.vicinity.adapter.bootloader;

import com.hits.vicinity.adapter.api.PNIPlacePodClient;
import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ClientBootloader implements ApplicationListener<ContextRefreshedEvent> {

    private PNIPlacePodClient placePodClient;

    public ClientBootloader(PNIPlacePodClient placePodClient) {
        this.placePodClient = placePodClient;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Logger.setLevel(Logger.DEBUG);
        placePodClient.getParkingLots();
    }

}
