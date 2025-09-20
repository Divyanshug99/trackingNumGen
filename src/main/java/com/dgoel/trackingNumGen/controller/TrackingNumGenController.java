package com.dgoel.trackingNumGen.controller;

import com.dgoel.trackingNumGen.service.TrackingNumGenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
public class TrackingNumGenController {

    private final TrackingNumGenService trackingNumGenService;

    public TrackingNumGenController(TrackingNumGenService trackingNumGenService) {
        this.trackingNumGenService = trackingNumGenService;
    }

    // curl "http://localhost:8080/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2018-11-20T19:29:32%2B08:00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics"
    @GetMapping("/next-tracking-number")
    public Map<String, String> getNextTrackingNumber(
            @RequestParam("origin_country_id") String originCountryId,
            @RequestParam("destination_country_id") String destinationCountryId,
            @RequestParam("weight") String weight,
            @RequestParam("created_at") String orderCreatedAt,
            @RequestParam("customer_id") UUID customerId,
            @RequestParam("customer_name") String customerName,
            @RequestParam("customer_slug") String customerSlug) {

        String trackingNumber = trackingNumGenService.generateTrackingNumber(originCountryId, destinationCountryId);

        return Map.of(
                "tracking_number", trackingNumber,
                "created_at", Instant.now().toString()
        );
    }
}