package Momentum.heatcaution.service;

import Momentum.heatcaution.dto.LoginRequest;
import Momentum.heatcaution.entity.User;
import Momentum.heatcaution.exception.PasswordMismatchException;
import Momentum.heatcaution.exception.UserNotFoundException;
import Momentum.heatcaution.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;

    private User buildAdmin() {
        return User.builder()
                .username("admin1")
                .password("admin-pass")
                .name("관리자")
                .phone("010-1234-5678")
                .birth(LocalDate.of(1999, 1, 1))
                .role(User.Role.ADMIN)
                .build();
    }

    private User buildUser() {
        return User.builder()
                .username("user1")
                .password("user-pass")
                .name("일반유저")
                .phone("010-0000-0000")
                .birth(LocalDate.of(2000, 1, 1))
                .role(User.Role.USER)
                .build();
    }

    @Test
    void login_관리자로그인_성공() {
        // given
        given(userRepository.findByUsername("admin1"))
                .willReturn(Optional.of(buildAdmin()));

        // when
        String result = adminService.login(new LoginRequest("admin1", "admin-pass"));

        // then
        assertThat(result).isEqualTo("관리자 로그인 성공");
    }

    @Test
    void login_아이디없음_예외발생() {
        // given
        given(userRepository.findByUsername("nope"))
                .willReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() ->
                adminService.login(new LoginRequest("nope", "pw"))
        ).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void login_비밀번호불일치_예외발생() {
        // given
        given(userRepository.findByUsername("admin1"))
                .willReturn(Optional.of(buildAdmin()));

        // when / then
        assertThatThrownBy(() ->
                adminService.login(new LoginRequest("admin1", "wrong"))
        ).isInstanceOf(PasswordMismatchException.class);
    }

    @Test
    void login_관리자권한없음_예외발생() {
        // given
        given(userRepository.findByUsername("user1"))
                .willReturn(Optional.of(buildUser()));

        // when / then
        assertThatThrownBy(() ->
                adminService.login(new LoginRequest("user1", "user-pass"))
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void logout_메시지확인() {
        // given
        String username = "admin1";

        // when
        String result = adminService.logout(username);

        // then
        assertThat(result).isEqualTo("admin1 관리자 로그아웃 성공");
    }
}
