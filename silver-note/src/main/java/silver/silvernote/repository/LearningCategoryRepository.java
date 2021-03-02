package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.LearningCategory;


public interface LearningCategoryRepository extends JpaRepository<LearningCategory, Long> {

}
