package Momentum.heatcaution.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProfileImageRequest(
        @Schema(description = "프로필 이미지 URL",
                example = "http://localhost:8080/images/profile1.png")
        String profileImageUrl) {
}
