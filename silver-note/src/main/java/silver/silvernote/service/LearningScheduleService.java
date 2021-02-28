package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.CenterLearning;
import silver.silvernote.repository.CenterLearningRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CenterLearningService {
    private final CenterLearningRepository centerLearningRepository;
    /**
     * 센터학습 등록
     */
    @Transactional
    public Long save(CenterLearning centerLearning) {
        centerLearningRepository.save(centerLearning);
        return centerLearning.getId();

    }

    /**
     * 정보 변경
     */


    /**
     * 센터학습 삭제
     */
    @Transactional
    public void deleteCenterLearning(Long id){
        centerLearningRepository.deleteById(id);
    }

    /**
     * 전체 센터학습 조회
     */
    public List<CenterLearning> findCenterLearnings() {
        return centerLearningRepository.findAll();
    }


    /**
     * 개별 센터학습 조회
     */
    public Optional<CenterLearning> findOne(Long centerLearningId) {
        return centerLearningRepository.findById(centerLearningId);
    }

    public Optional<CenterLearning> findOneByDate(LocalDate date) {
        return centerLearningRepository.findByDate(date);
    }


}
