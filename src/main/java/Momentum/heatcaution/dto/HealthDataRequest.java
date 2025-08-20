package Momentum.heatcaution.dto;

import java.time.LocalDateTime;

public record HealthDataRequest (
        LocalDateTime measurementTime,
        Double heartRate,
        Double bodyTemperature
) {}
