package Momentum.heatcaution.repository;

import Momentum.heatcaution.entity.HealthData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthDataRepository extends JpaRepository<HealthData,Long> {
}
