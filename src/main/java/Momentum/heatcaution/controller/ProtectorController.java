package Momentum.heatcaution.controller;

import Momentum.heatcaution.dto.ProtectorDto;
import Momentum.heatcaution.service.ProtectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Protector API", description = "보호자 정보 관리 API")
@RestController
@RequestMapping("/api/dashboard/protector")
@RequiredArgsConstructor
public class ProtectorController {

    private final ProtectorService protectorService;

    /**
     * 현재 로그인한 사용자의 username을 세션에서 가져오는 헬퍼 메서드
     */
    private String getUsernameFromSession(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        return username;
    }

    /**
     * 보호자 연락처 등록 API
     * POST /api/dashboard/protector
     */
    @Operation(summary = "보호자 등록", description = "로그인된 사용자의 보호자를 새로 등록합니다. 한 사용자당 최대 5명까지 등록할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "보호자 등록 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 유효성 검증 실패 (예: 전화번호 형식 오류)"),
            @ApiResponse(responseCode = "500", description = "서버 오류 (예: 보호자 등록 한도 초과)")
    })
    @PostMapping
    public ResponseEntity<ProtectorDto> addProtector(@Valid @RequestBody ProtectorDto protectorDto, HttpSession session) {
        String username = getUsernameFromSession(session);
        ProtectorDto newProtector = protectorService.addProtector(username, protectorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProtector);
    }

    /**
     * 로그인한 사용자의 보호자 목록 조회 API
     * GET /api/dashboard/protector
     */
    @Operation(summary = "보호자 목록 조회", description = "현재 로그인된 사용자의 모든 보호자 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
    })
    @GetMapping
    public ResponseEntity<List<ProtectorDto>> getMyProtectors(HttpSession session) {
        String username = getUsernameFromSession(session);
        List<ProtectorDto> protectors = protectorService.getProtectors(username);
        return ResponseEntity.ok(protectors);
    }

    /**
     * 보호자 정보 수정 API
     * PUT /api/dashboard/protector/{protectorId}
     */
    @Operation(summary = "보호자 정보 수정", description = "특정 보호자(protectorId)의 정보를 수정합니다. 본인의 보호자만 수정 가능합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "해당 보호자를 찾을 수 없거나 수정 권한 없음")
    })
    @PutMapping("/{protectorId}")
    public ResponseEntity<ProtectorDto> updateProtector(@PathVariable Long protectorId,
                                                        @Valid @RequestBody ProtectorDto protectorDto,
                                                        HttpSession session) {
        String username = getUsernameFromSession(session);
        ProtectorDto updatedProtector = protectorService.updateProector(username, protectorId, protectorDto);
        return ResponseEntity.ok(updatedProtector);
    }

    /**
     * 보호자 정보 삭제 API
     * DELETE /api/dashboard/protector/{protectorId}
     */
    @Operation(summary = "보호자 정보 삭제", description = "특정 보호자(protectorId)를 삭제합니다. 본인의 보호자만 삭제 가능합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공 (No Content)"),
            @ApiResponse(responseCode = "404", description = "해당 보호자를 찾을 수 없거나 삭제 권한 없음")
    })
    @DeleteMapping("/{protectorId}")
    public ResponseEntity<Void> deleteProtector(@PathVariable Long protectorId, HttpSession session) {
        String username = getUsernameFromSession(session);
        protectorService.deleteProtector(username, protectorId);
        return ResponseEntity.noContent().build();
    }
}
