package Momentum.heatcaution.repository;

import Momentum.heatcaution.entity.Protector;
import Momentum.heatcaution.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProtectorRepository extends JpaRepository<Protector,Long> {

    /**
     * 특정 User에 속한 모든 Protector를 조회
     */
    List<Protector> findByUser(User user);

    /**
     * 특정 User에 속한 Protector의 수를 계산
     * 보호자 등록 시 최대 5명 제한을 확인하기 위해 사용
     */
    long countByUser(User user);

    /**
     * 특정 User에게 속한 특정 Protector ID를 조회
     * 수정, 삭제 시 해당 Protector가 요청을 보낸 User의 소유인지 확인
     */
    Optional<Protector> findByIdAndUser(Long id, User user);
}
