package Momentum.heatcaution.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProtectorDto {

    private Long id; // 수정, 삭제 시 사용

    @NotBlank(message = "보호자 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "관계는 필수입니다.")
    private String relation;

    @NotBlank(message = "연락처는 필수입니다.")
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "유효한 휴대폰 번호 형식이 아닙니다.")
    private String phone;
}