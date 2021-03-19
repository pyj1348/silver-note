package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.Learning;
import silver.silvernote.domain.LearningCategory;
import silver.silvernote.repository.LearningCategoryRepository;
import silver.silvernote.repository.LearningRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional // (readOnly = true) // 조회만 하는 서비스에선 readOnly = true 사용 권장, 성능이 좋다
// 생성자주입 때 lombok을 쓸 거면 @AllArgsConstructor | @RequiredArgsConstructor(final 요소만)를 사용
public class LearningService {
    private final LearningRepository learningRepository;
    private final LearningCategoryRepository categoryRepository;
    /**
     * 학습 등록
     */
    @Transactional
    public Long save(Learning learning) {
        learningRepository.save(learning);
        return learning.getId();

    }

    /**
     * 정보 변경
     */
    @Transactional
    public void updateData(Long id, String name, String url, String briefDescription, String fullDescription){
        Learning learning = learningRepository.findById(id).orElseThrow(NoSuchElementException::new);
        learning.updateData(name, url, briefDescription, fullDescription);
        learningRepository.save(learning);
    }


    /**
     * 학습 삭제
     */

    @Transactional
    public void deleteLearning(Long id){
        learningRepository.deleteById(id);
    }

    /**
     * 전체 학습 조회
     */
    public List<Learning> findLearnings() {
        return learningRepository.findAll();
    }
    public List<Learning> findLearningsByIds(List<Long> ids) {
        return learningRepository.findAllById(ids);
    }

    public List<Learning> findLearningsByCategory(LearningCategory category){

        return learningRepository.findAllByCategory(category);
    }


    /**
     * 개별 학습 조회
     */
    public Optional<Learning> findOne(Long learningId) {
        return learningRepository.findById(learningId);
    }
    public Optional<Learning> findOneByCategoryId(Long categoryId) {

        LearningCategory category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);

        return learningRepository.findByCategory(category);
    }


}
