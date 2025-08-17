package Momentum.heatcaution.service;

import Momentum.heatcaution.dto.LoginRequest;
import Momentum.heatcaution.dto.RegisterRequest;
import Momentum.heatcaution.entity.User;
import Momentum.heatcaution.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Momentum.heatcaution.exception.UserNotFoundException;
import Momentum.heatcaution.exception.PasswordMismatchException;


import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long register(RegisterRequest req) {

        // 엔티티 변환
        User user = User.builder()
                .username(req.username())
                .password(req.password()) // 암호화 없음
                .name(req.name())
                .phone(req.phone())
                .birth(LocalDate.parse(req.birth()))
                .build();

        return userRepository.save(user).getId();
    }
    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(UserNotFoundException::new);

        if (!user.getPassword().equals(request.password())) {
            throw new PasswordMismatchException();
        }

        return "로그인 성공";
    }
    //로그아웃
    public String logout(String username) {
        return username + " 로그아웃 성공";
    }

    //사용자 이름 반환
    @Transactional(readOnly = true)
    public String getNameByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new)
                .getName();
    }
}