package Momentum.heatcaution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "사용자 아이디 변경 요청 DTO")
public record UpdateNameRequest(
        @NotBlank(message = "새 이름은 비어 있을 수 없습니다.")
        String newName
) {}
