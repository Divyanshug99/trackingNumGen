package com.dgoel.trackingNumGen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TrackingNumGenService {

    private final long workerId;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public TrackingNumGenService(@Value("${worker.id}") long workerId) {
        this.workerId = workerId;
    }

    public String generateTrackingNumber(String originCountry, String destinationCountry) {
        String prefix = originCountry.toUpperCase().substring(0, 2) + destinationCountry.toUpperCase().substring(0, 2);

        long uniqueId = nextId();

        String uniqueSuffix = toBase36Upper(uniqueId);
        String paddedSuffix = String.format("%12s", uniqueSuffix).replace(' ', '0');

        return prefix + paddedSuffix;
    }

    private synchronized long nextId() {
        long currentTimestamp = Instant.now().toEpochMilli();

        sequence = (currentTimestamp == lastTimestamp) ? sequence + 1 : 0L;
        lastTimestamp = currentTimestamp;

        return (currentTimestamp << 22) | (workerId << 12) | sequence;
    }

    private String toBase36Upper(long val) {
        return Long.toUnsignedString(val, 36).toUpperCase();
    }
}