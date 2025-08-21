package Momentum.heatcaution.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ai_predictions")
public class Ai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 유저 id (FK)

    private Double heartRate;
    private LocalDateTime predictionTime = LocalDateTime.now(); // *시간은 상세페이지를 위해 필요함*

    @Builder
    public Ai(User user, Double heartRate, LocalDateTime predictionTime) {
        this.user = user;
        this.heartRate = heartRate;
        if (predictionTime != null) this.predictionTime = predictionTime;
    }
}


