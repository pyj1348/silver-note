package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import silver.silvernote.domain.LearningCategory;

import java.util.List;


public interface LearningCategoryRepository extends JpaRepository<LearningCategory, Long> {

    @Query("SELECT lc FROM LearningCategory lc WHERE lc.id = lc.parent.id")
    List<LearningCategory> findAllTopCategory();

    @Query("SELECT lc FROM LearningCategory lc WHERE lc.parent.id = :parentId AND lc.id <> :parentId")
    List<LearningCategory> findAllByParent(@Param("parentId")Long parentId);

}
