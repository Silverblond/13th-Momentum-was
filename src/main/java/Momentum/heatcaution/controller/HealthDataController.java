package Momentum.heatcaution.controller;

import Momentum.heatcaution.dto.HealthDataRequest;
import Momentum.heatcaution.exception.LoginRequiredException;
import Momentum.heatcaution.service.HealthDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health Data API", description = "건강 데이터 수신 API")
@RestController
@RequestMapping("/api/healthdata")
@RequiredArgsConstructor
public class HealthDataController {

    private final HealthDataService healthDataService;

    @Operation(summary = "건강 데이터 수신 및 저장", description = "애플워치에서 측정한 건강 데이터를 수신하여 저장합니다.")
    @ApiResponse(responseCode = "201", description = "데이터 저장 성공")
    @PostMapping
    public ResponseEntity<?> receiveHealthData(
            @RequestBody HealthDataRequest healthDataRequest,
            @Parameter(hidden = true) HttpSession session) {

        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            throw new LoginRequiredException();
        }

        healthDataService.saveHealthData(username, healthDataRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
