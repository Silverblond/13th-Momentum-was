package Momentum.heatcaution.controller;

import Momentum.heatcaution.dto.LoginRequest;
import Momentum.heatcaution.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Admin API", description = "관리자용 API")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "관리자 로그인", description = "관리자 계정으로 로그인합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @PostMapping("/login")
    public ResponseEntity<String> adminLogin(@RequestBody LoginRequest request) {
        String result = adminService.login(request);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "관리자 로그아웃", description = "관리자 계정을 로그아웃 처리합니다. (Stateless 방식으로, 클라이언트 측에서 토큰 삭제 등을 처리해야 함)")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    @PostMapping("/logout")
    public ResponseEntity<String> adminLogout(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String result = adminService.logout(username);
        return ResponseEntity.ok(result);
    }
}

