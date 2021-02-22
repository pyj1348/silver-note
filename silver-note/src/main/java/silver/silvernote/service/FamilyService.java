package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.member.Family;
import silver.silvernote.repository.member.FamilyRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional // (readOnly = true) // 조회만 하는 서비스에선 readOnly = true 사용 권장, 성능이 좋다
// 생성자주입 때 lombok을 쓸 거면 @AllArgsConstructor | @RequiredArgsConstructor(final 요소만)를 사용
public class FamilyService {
    private final FamilyRepository familyRepository;
    /**
     * 가족 등록
     */
    @Transactional
    public Long save(Family family) {
        familyRepository.save(family);
        return family.getId();
    }


    /**
     * 가족 삭제
     */

    @Transactional
    public void deleteFamily(Long id){
        familyRepository.deleteById(id);
    }


    /**
     * 전체 가족 조회
     */

    public List<Family> findFamilies() {
        return familyRepository.findAll();
    }


    /**
     * 개별 가족 조회
     */
    public Optional<Family> findOne(Long exerciseId) {
        return familyRepository.findById(exerciseId);
    }
    public Optional<Family> findOne(String name, String rnn) {
        return familyRepository.findByNameAndRrn(name, rnn);
    }

}
