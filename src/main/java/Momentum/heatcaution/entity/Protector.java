package Momentum.heatcaution.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "protectors")
public class Protector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 보호자 id (PK)

    // 관계 매핑: 한 명의 보호자는 반드시 한 명의 유저에게 종속된다 (N:1)
    // @JoinColumn은 외래키(FK)를 지정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 유저 id (FK)

    @Column(nullable = false)
    private String name; // 보호자 이름

    @Column(nullable = false)
    private String relation; // 관계(부, 모, 친척, 지인 등)

    @Column(nullable = false)
    private String phone; // 연락처
}