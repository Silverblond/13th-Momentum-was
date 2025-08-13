package Momentum.heatcaution.service;

import Momentum.heatcaution.dto.RegisterRequest;
import Momentum.heatcaution.entity.User;
import Momentum.heatcaution.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import Momentum.heatcaution.dto.LoginRequest;
import Momentum.heatcaution.exception.PasswordMismatchException;
import Momentum.heatcaution.exception.UserNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void register_회원가입요청이들어오면_유저정보가저장() {
        // given
        RegisterRequest request = new RegisterRequest(
                "testuser",
                "password123",
                "홍길동",
                "010-1234-5678",
                "1990-01-01"
        );

        User mockUser = User.builder()
                .username("testuser")
                .password("password123")
                .name("홍길동")
                .phone("010-1234-5678")
                .birth(LocalDate.parse("1990-01-01"))
                .build();

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // when
        userService.register(request);

        // then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void login_유효한요청이면_성공() {
        // given
        LoginRequest request = new LoginRequest("testuser", "password123");

        User mockUser = User.builder()
                .username("testuser")
                .password("password123")
                .build();

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        // when
        String result = userService.login(request);

        // then
        assertEquals("로그인 성공", result);
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void login_존재하지않는유저면_예외발생() {
        // given
        LoginRequest request = new LoginRequest("nouser", "password");

        when(userRepository.findByUsername("nouser")).thenReturn(Optional.empty());

        // when & then
        assertThrows(UserNotFoundException.class, () -> userService.login(request));
    }

    @Test
    void login_비밀번호틀리면_예외발생() {
        // given
        LoginRequest request = new LoginRequest("testuser", "wrongpw");

        User mockUser = User.builder()
                .username("testuser")
                .password("correctpw")
                .build();

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));

        // when & then
        assertThrows(PasswordMismatchException.class, () -> userService.login(request));
    }


}
