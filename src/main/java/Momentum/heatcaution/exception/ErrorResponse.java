package Momentum.heatcaution.exception;


import java.time.LocalDateTime;

public record ErrorResponse(
        String code,      // 예: VALIDATION_ERROR, DUPLICATE_USERNAME
        String message,
        LocalDateTime timestamp
) {
    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message, LocalDateTime.now());
    }
}
