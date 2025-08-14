package Momentum.heatcaution.service;

import Momentum.heatcaution.dto.ProtectorDto;
import Momentum.heatcaution.entity.Protector;
import Momentum.heatcaution.entity.User;
import Momentum.heatcaution.repository.ProtectorRepository;
import Momentum.heatcaution.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProtectorService {

    private final ProtectorRepository protectorRepository;
    private final UserRepository userRepository;

    private static final int MAX_PROTECTOR_COUNT = 5;

    /**
     * 보호자 등록
     * @param username 현재 로그인된 사용자 ID
     * @param protectorDto 등록할 보호자 정보
     * @return 등록된 보호자 정보
     */
    public ProtectorDto addProtector(String username, ProtectorDto protectorDto) {
        User user = findUserByUsername(username);

        if (protectorRepository.countByUser(user) >= MAX_PROTECTOR_COUNT) {
            throw new IllegalStateException("보호자는 최대 " + MAX_PROTECTOR_COUNT + "명까지 등록할 수 있습니다.");
        }

        Protector protector = new Protector();
        protector.setUser(user);
        protector.setName(protectorDto.getName());
        protector.setRelation(protectorDto.getRelation());
        protector.setPhone(protectorDto.getPhone());

        Protector savedProtector = protectorRepository.save(protector);
        return convertToDto(savedProtector);
    }

    /**
     * 특정 사용자의 모든 보호자 조회
     * @param username 현재 로그인된 사용자 ID
     * @return 해당 사용자의 보호자 목록
     */
    @Transactional(readOnly = true)
    public List<ProtectorDto> getProtectors(String username) {
        User user = findUserByUsername(username);
        return protectorRepository.findByUser(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * 보호자 정보 수정
     * @param username 현재 로그인된 사용자 ID
     * @param protectorId 수정할 보호자 ID
     * @param protectorDto 수정할 정보
     * @return 수정된 보호자 정보
     */
    public ProtectorDto updateProector(String username, Long protectorId, ProtectorDto protectorDto) {
        User user = findUserByUsername(username);

        Protector protector = protectorRepository.findByIdAndUser(protectorId, user)
                .orElseThrow(() -> new EntityNotFoundException("해당 보호자를 찾을 수 없거나 수정할 권한이 없습니다. ID: " + protectorId));

        protector.setName(protectorDto.getName());
        protector.setPhone(protectorDto.getPhone());
        protector.setRelation(protectorDto.getRelation());

        Protector updatedProtector = protectorRepository.save(protector);
        return convertToDto(updatedProtector);
    }

    /**
     * 보호자 정보 삭제
     * @param username 현재 로그인된 사용자 ID
     * @param protectorId 삭제할 보호자 ID
     */
    public void deleteProtector(String username, Long protectorId) {
        User user = findUserByUsername(username);

        Protector protector = protectorRepository.findByIdAndUser(protectorId, user)
                .orElseThrow(() -> new EntityNotFoundException("해당 보호자를 찾을 수 없거나 삭제할 권한이 없습니다. ID: " + protectorId));

        protectorRepository.delete(protector);
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다." + username));
    }

    private ProtectorDto convertToDto(Protector protector) {
        ProtectorDto dto = new ProtectorDto();
        dto.setId(protector.getId());
        dto.setName(protector.getName());
        dto.setPhone(protector.getPhone());
        return dto;
    }
}
