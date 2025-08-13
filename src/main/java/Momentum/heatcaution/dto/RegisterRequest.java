package Momentum.heatcaution.dto;

import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        String username,
        String password,
        String name,
        @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "전화번호 형식은 010-1234-5678입니다")
        String phone,
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "생년월일 형식은 yyyy-MM-dd입니다")
        String birth
) {}
