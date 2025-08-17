package Momentum.heatcaution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "사용자 아이디 변경 요청 DTO")
public record UpdateUsernameRequest (
    @NotBlank(message = "새로운 아이디를 입력해주세요.")
    @Schema(description = "변경할 새로운 아이디", example = "new_testuser")
    String newUsername
) {}
