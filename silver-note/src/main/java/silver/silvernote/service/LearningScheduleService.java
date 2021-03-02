package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.LearningSchedule;
import silver.silvernote.repository.LearningScheduleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LearningScheduleService {
    private final LearningScheduleRepository learningScheduleRepository;
    /**
     * 센터학습 등록
     */
    @Transactional
    public Long save(LearningSchedule learningSchedule) {
        learningScheduleRepository.save(learningSchedule);
        return learningSchedule.getId();

    }

    /**
     * 정보 변경
     */


    /**
     * 센터학습 삭제
     */
    @Transactional
    public void deleteLearningSchedule(Long id){
        learningScheduleRepository.deleteById(id);
    }

    /**
     * 전체 센터학습 조회
     */
    public List<LearningSchedule> findSchedules() {
        return learningScheduleRepository.findAll();
    }

    public List<LearningSchedule> findSchedulesByCenter(Center center) {
        return learningScheduleRepository.findAllByCenter(center);
    }

    public List<LearningSchedule> findSchedulesByCenterAndDate(Center center, LocalDate start, LocalDate end) {
        return learningScheduleRepository.findAllByCenterAndDateBetweenOrderByDate(center, start, end);
    }


    /**
     * 개별 센터학습 조회
     */
    public Optional<LearningSchedule> findOne(Long learningScheduleId) {
        return learningScheduleRepository.findById(learningScheduleId);
    }

    public Optional<LearningSchedule> findOneByDate(LocalDate date) {
        return learningScheduleRepository.findByDate(date);
    }

    public Optional<LearningSchedule> findOneByDateAndCenter(LocalDate date, Center center) {
        return learningScheduleRepository.findByDateAndCenter(date, center);
    }


}
