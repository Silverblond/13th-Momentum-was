package Momentum.heatcaution.dto;

import Momentum.heatcaution.entity.HealthData;

import java.time.LocalDateTime;

public record HealthDataDto (
        Long id,
        LocalDateTime measurementTime,
        Double heartRate,
        Double bodyTemperature
) {
    public static HealthDataDto from(HealthData entity) {

        if (entity == null) {
            return null;
        }

        return new HealthDataDto(
                entity.getId(),
                entity.getMeasurementTime(),
                entity.getHeartRate(),
                entity.getBodyTemperature()
        );
    }
}
