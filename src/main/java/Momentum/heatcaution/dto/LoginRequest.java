package Momentum.heatcaution.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 요청 DTO")
public record LoginRequest(
        @Schema(description = "사용자 아이디", example = "testuser")
        String username,

        @Schema(description = "비밀번호", example = "password1234")
        String password
) {}