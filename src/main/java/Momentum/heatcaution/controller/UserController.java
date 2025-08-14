package Momentum.heatcaution.controller;

import Momentum.heatcaution.dto.LoginRequest;
import Momentum.heatcaution.dto.RegisterRequest;
import Momentum.heatcaution.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        Long userId = userService.register(request);
        return ResponseEntity.status(201).body(userId); //새로운 user 생성 201
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        String loginResult = userService.login(request);
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("loggedInUser", request.username());
        return ResponseEntity.ok(loginResult);
    }

    //로그아웃
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
}
