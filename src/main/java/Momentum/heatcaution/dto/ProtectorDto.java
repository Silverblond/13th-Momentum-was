package Momentum.heatcaution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "보호자 정보 DTO")
public class ProtectorDto {

    @Schema(description = "보호자 ID (응답 시에만 포함됨)", example = "1")
    private Long id; // 수정, 삭제 시 사용

    @Schema(description = "보호자 이름", example = "김보호")
    @NotBlank(message = "보호자 이름은 필수입니다.")
    private String name;

    @Schema(description = "사용자와의 관계", example = "부")
    @NotBlank(message = "관계는 필수입니다.")
    private String relation;

    @Schema(description = "보호자 연락처", example = "010-1111-2222")
    @NotBlank(message = "연락처는 필수입니다.")
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "유효한 휴대폰 번호 형식이 아닙니다.")
    private String phone;
}