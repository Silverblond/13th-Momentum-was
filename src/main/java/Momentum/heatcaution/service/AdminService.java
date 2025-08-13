package Momentum.heatcaution.service;

import Momentum.heatcaution.dto.LoginRequest;
import Momentum.heatcaution.entity.User;
import Momentum.heatcaution.exception.PasswordMismatchException;
import Momentum.heatcaution.exception.UserNotFoundException;
import Momentum.heatcaution.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(UserNotFoundException::new);

        if (!user.getPassword().equals(request.password())) {
            throw new PasswordMismatchException();
        }

        if (!User.Role.ADMIN.equals(user.getRole())) {
            throw new IllegalStateException("관리자 권한이 없습니다.");
        }

        return "관리자 로그인 성공";
    }

    public String logout(String username) {
        return username + " 관리자 로그아웃 성공";
    }
}

