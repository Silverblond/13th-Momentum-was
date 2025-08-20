package Momentum.heatcaution.repository;

import Momentum.heatcaution.entity.HealthData;
import Momentum.heatcaution.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthDataRepository extends JpaRepository<HealthData,Long> {

    /**
     * 특정 사용자의 가장 최근 측정 기록 1개를 조회하는 메서드
     * findTop: 가장 첫번째 결과 1개만 가져오라는 의미
     * findTopByUserOrderByMeasurementTimeDesc: measureTime(측정시각)을 기준으로 내림차순(최신순) 정렬
     */
    Optional<HealthData> findTopByUserOrderByMeasurementTimeDesc(User user);
}
