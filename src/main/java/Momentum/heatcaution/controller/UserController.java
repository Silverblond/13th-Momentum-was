package Momentum.heatcaution.controller;

import Momentum.heatcaution.dto.LoginRequest;
import Momentum.heatcaution.dto.RegisterRequest;
import Momentum.heatcaution.exception.LoginRequiredException;
import Momentum.heatcaution.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "User API", description = "일반 사용자용 API (회원가입, 로그인, 로그아웃)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    // 회원가입
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공, 생성된 사용자 ID 반환"),
            @ApiResponse(responseCode = "400", description = "입력값 유효성 검증 실패 (전화번호, 생년월일 형식 등)"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 아이디일 경우 (Conflict)")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        Long userId = userService.register(request);
        return ResponseEntity.status(201).body(userId); //새로운 user 생성 201
    }

    // 로그인
    @Operation(summary = "사용자 로그인", description = "사용자 계정으로 로그인하고, 서버에 세션(로그인 상태)을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공 및 세션 쿠키(JSESSIONID) 발급"),
            @ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호 불일치 (Unauthorized)")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        String loginResult = userService.login(request);
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("loggedInUser", request.username());
        return ResponseEntity.ok(loginResult);
    }

    //로그아웃
    @Operation(summary = "사용자 로그아웃", description = "서버의 현재 세션을 무효화하여 로그아웃 처리합니다.")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if (session != null) {
            String username = (String) session.getAttribute("loggedInUser");
            session.invalidate();
            String logoutMessage = userService.logout(username);
            return ResponseEntity.ok(logoutMessage);
        }
        return ResponseEntity.ok("로그아웃할 세션이 없습니다.");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyUsername(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            throw new LoginRequiredException();
        }
        return ResponseEntity.ok(Map.of("username", username));
    }
}
