package Momentum.heatcaution.dto;

import Momentum.heatcaution.entity.HealthData;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "건강 데이터 응답 DTO")
public record HealthDataDto (
        @Schema(description = "데이터 고유 ID", example = "101")
        Long id,

        @Schema(description = "측정 시각", example = "2025-08-21T14:30:00")
        LocalDateTime measurementTime,

        @Schema(description = "심박수 (bpm)", example = "85.0")
        Double heartRate,

        @Schema(description = "체온 (섭씨)", example = "36.7")
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
