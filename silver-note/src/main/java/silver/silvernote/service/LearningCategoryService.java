package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.Album;
import silver.silvernote.domain.LearningCategory;
import silver.silvernote.repository.LearningCategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LearningCategoryService {
    private final LearningCategoryRepository learningCategoryRepository;
    /**
     * 카테고리 등록
     */
    @Transactional
    public Long save(LearningCategory category) {
        learningCategoryRepository.save(category);
        return category.getId();
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteCategory(Long id){
        learningCategoryRepository.deleteById(id);
    }

    /**
     * 전체 카테고리 조회
     */
    public List<LearningCategory> findCategories() {
        return learningCategoryRepository.findAll();
    }

    /**
     * 최상위 카테고리 조회
     */
    public List<LearningCategory> findMainCategories() {
        return learningCategoryRepository.findAllByParentId(null);
    }

    /**
     * 하위 카테고리 조회
     */
    public List<LearningCategory> findSubCategories(Long parentId) {
        return learningCategoryRepository.findAllByParentId(parentId);
    }

    /**
     * 개별 일정 조회
     */
    public Optional<LearningCategory> findOne(Long categoryId) {
        return learningCategoryRepository.findById(categoryId);
    }
}
