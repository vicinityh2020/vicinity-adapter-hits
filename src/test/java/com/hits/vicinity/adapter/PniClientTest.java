package com.hits.vicinity.adapter;

import com.hits.vicinity.adapter.api.pni.PniClient;
import com.hits.vicinity.adapter.domain.pni.ParkingLot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PniClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PniClient client;

    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(client, "baseURI", "api.pnicloud.com");
        ReflectionTestUtils.setField(client, "apiKey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiJjb21wYW5pZXNBY2NvdW50c0lkIiwidW5pcXVlX25hbWUiOiJaTEhuVFRoa0RId3RkSHA1USIsIm5iZiI6MTUyNDU2NzgxMiwiZXhwIjoxNjgyMzM0MjEyLCJpYXQiOjE1MjQ1Njc4MTIsImlzcyI6Imh0dHBzOi8vd3d3LnBuaWNvcnAuY29tIiwiYXVkIjoiaHR0cHM6Ly93d3cucG5pY29ycC5jb20ifQ.b7SdO4YGUbH6UtPEjWMStwDg_h6fhzjPvnVSz3SH2i4");
        ReflectionTestUtils.setField(client, "apiVersion", "api");
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getParkingLots() {
        List<ParkingLot> parkingLots = client.getParkingLots();
        assertTrue(parkingLots.size() > 0);
    }
}
