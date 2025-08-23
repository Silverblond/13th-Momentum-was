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
        System.out.println(req.birth());
        System.out.println(req.password());
        System.out.println(req.name());


        // 엔티티 변환
        User user = User.builder()
                .username(req.username())
                .password(req.password()) // 암호화 없음
                .name(req.name())
                .phone(req.phone())
                .birth(req.birth())
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

    @Transactional
    public String updateUsername(String currentUsername, String newUsername) {
        //새로운 아이디가 이미 존재하는지
        if (userRepository.existsByUsername(newUsername)) {
            throw new IllegalStateException("이미 사용 중인 아이디 입니다.");
        }
        //현재 사용자 DB 조회
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(UserNotFoundException::new);

        //User 엔티티의 username 변경
        user.updateUsername(newUsername);

        return user.getUsername();
    }

    //프로필 이미지 URL 저장
    @Transactional
    public String saveProfileImageUrl(String username, String imageUrl) {
        //DB 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        //ProfileImageUrl 변경
        user.setProfileImageUrl(imageUrl);
        userRepository.save(user);

        return user.getProfileImageUrl();
    }

    //프로필 이미지 수정
    @Transactional
    public String updateProfileImageUrl(String username, String newImageUrl) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        user.setProfileImageUrl(newImageUrl);
        userRepository.save(user);

        return user.getProfileImageUrl();
    }

    //프로필 이미지 조회
    @Transactional(readOnly = true)
    public String getProfileImageUrl(String username) {
        //DB 조회
        return userRepository.findByUsername(username)
            .orElseThrow(UserNotFoundException::new)
                .getProfileImageUrl();
    }
}
