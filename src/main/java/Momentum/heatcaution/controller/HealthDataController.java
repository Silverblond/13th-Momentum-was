package Momentum.heatcaution.controller;

import Momentum.heatcaution.dto.HealthDataDto;
import Momentum.heatcaution.dto.HealthDataRequest;
import Momentum.heatcaution.exception.LoginRequiredException;
import Momentum.heatcaution.service.HealthDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Health Data API", description = "건강 데이터 수신 API")
@RestController
@RequestMapping("/api/healthdata")
@RequiredArgsConstructor
public class HealthDataController {

    private final HealthDataService healthDataService;

    @Operation(summary = "건강 데이터 수신 및 저장", description = "애플워치에서 측정한 건강 데이터를 수신하여 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "데이터 저장 성공"),
            @ApiResponse(responseCode = "400", description = "요청 본문(JSON)이 잘못되었을 경우", content = @Content),
            @ApiResponse(responseCode = "401", description = "로그인이 필요할 경우", content = @Content),
            @ApiResponse(responseCode = "404", description = "세션의 사용자를 DB에서 찾을 수 없는 경우", content = @Content)
    })
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

    @Operation(summary = "가장 최근 건강 데이터 조회", description = "로그인된 사용자의 가장 마지막 측정 기록 1개를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "데이터 조회 성공 (데이터가 없으면 본문이 비어있음)"),
            @ApiResponse(responseCode = "401", description = "로그인이 필요할 경우", content = @Content),
            @ApiResponse(responseCode = "404", description = "세션의 사용자를 DB에서 찾을 수 없는 경우", content = @Content)
    })
    @GetMapping("/latest")
    public ResponseEntity<?> getMyLastHealthData(@Parameter(hidden = true) HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            throw new LoginRequiredException();
        }

        HealthDataDto lastestData = healthDataService.getHealthDataForUser(username);
        return ResponseEntity.ok(lastestData);
    }
}