package Momentum.heatcaution.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "건강 데이터 생성 요청 DTO")
public record HealthDataRequest (
        @Schema(description = "측정 시각", example = "2025-08-21T14:30:00")
        LocalDateTime measurementTime,

        @Schema(description = "심박수 (bpm)", example = "85.0")
        Double heartRate,

        @Schema(description = "체온 (섭씨)", example = "36.7")
        Double bodyTemperature
) {}
