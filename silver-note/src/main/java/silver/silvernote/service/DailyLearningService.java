package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.DailyLearning;
import silver.silvernote.domain.Learning;
import silver.silvernote.domain.LearningCategory;
import silver.silvernote.domain.LearningSchedule;
import silver.silvernote.repository.DailyLearningRepository;
import silver.silvernote.repository.LearningCategoryRepository;
import silver.silvernote.repository.LearningRepository;
import silver.silvernote.repository.LearningScheduleRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyLearningService {
    private final DailyLearningRepository dailyLearningRepository;
    /**
     * 학습 등록
     */
    @Transactional
    public Long save(DailyLearning learning) {
        dailyLearningRepository.save(learning);
        return learning.getId();

    }

    /**
     * 정보 변경
     */

    /**
     * 학습 삭제
     */

    @Transactional
    public void deleteDailyLearning(Long id){
        dailyLearningRepository.deleteById(id);
    }

    /**
     * 전체 학습 조회
     */
    public List<DailyLearning> findLearnings() {
        return dailyLearningRepository.findAll();
    }

    public List<DailyLearning> findLearningsBySchedule(LearningSchedule schedule) {
        return dailyLearningRepository.findAllBySchedule(schedule);
    }


    /**
     * 개별 학습 조회
     */
    public Optional<DailyLearning> findOne(Long learningId) {
        return dailyLearningRepository.findById(learningId);
    }

}
