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
@Table(name = "health_data")
public class HealthData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 유저 id (FK)

    private Double heartRate; // 심박수
    private Double bodyTemperature; // 체온
    private LocalDateTime measurementTime; // 측정 시간

    @Builder
    public HealthData(User user, Double heartRate, Double bodyTemperature, LocalDateTime measurementTime) {
        this.user = user;
        this.heartRate = heartRate;
        this.bodyTemperature = bodyTemperature;
        this.measurementTime = measurementTime;
    }
}


