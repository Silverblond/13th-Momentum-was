package Momentum.heatcaution.service;

import Momentum.heatcaution.dto.HealthDataRequest;
import Momentum.heatcaution.entity.HealthData;
import Momentum.heatcaution.entity.User;
import Momentum.heatcaution.exception.UserNotFoundException;
import Momentum.heatcaution.repository.HealthDataRepository;
import Momentum.heatcaution.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HeathDataService {
    private final HealthDataRepository healthDataRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveHealthData(String username, HealthDataRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        HealthData healthData = HealthData.builder()
                .user(user)
                .measurementTime(request.measurementTime())
                .heartRate(request.heartRate())
                .bodyTemperature(request.bodyTemperature())
                .build();

        healthDataRepository.save(healthData);
    }
}
