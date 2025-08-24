package Momentum.heatcaution.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true) //DB 중복 방지
    private String username;
    private String password;
    private String name;
    private LocalDate birth;
    private String phone;
    private Boolean hasWatch = false;
    private LocalDateTime updateAt;
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;
/*
상태 정의 후 작성
    @Enumerated(EnumType.STRING)
    private Status status;
*/

    @Builder
    public User(String username, String password, String name, String phone, LocalDate birth, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.role = (role != null ? role : Role.USER); // 전달값 없으면 USER
    }

    // name 업데이트
    public void updateName(String newName) {
        this.name = newName;
    }

    public enum Role {
        USER, ADMIN
    }
    //profileImageUrl 업데이트
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
