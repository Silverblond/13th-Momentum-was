package Momentum.heatcaution.controller;

import Momentum.heatcaution.dto.ProtectorDto;
import Momentum.heatcaution.service.ProtectorService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @DeleteMapping("/{protectorId}")
    public ResponseEntity<Void> deleteProtector(@PathVariable Long protectorId, HttpSession session) {
        String username = getUsernameFromSession(session);
        protectorService.deleteProtector(username, protectorId);
        return ResponseEntity.noContent().build();
    }
}
