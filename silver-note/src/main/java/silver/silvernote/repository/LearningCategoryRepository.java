package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.LearningCategory;

import java.util.List;


public interface LearningCategoryRepository extends JpaRepository<LearningCategory, Long> {

    List<LearningCategory> findAllByParentId(Long parentId);

}
