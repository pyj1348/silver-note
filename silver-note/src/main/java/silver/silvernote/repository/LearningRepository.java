package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import silver.silvernote.domain.Learning;
import silver.silvernote.domain.LearningCategory;

import java.util.List;
import java.util.Optional;

public interface LearningRepository extends JpaRepository<Learning, Long> {

    Optional<Learning> findByName(String name);

    Optional<Learning> findByCategory(LearningCategory category);

//    @Query(value = "select DISTINCT l from Learning l join fetch l.category")
    List<Learning> findAllByCategory(LearningCategory category);

    @Query("SELECT l FROM Learning l WHERE l.id in :ids")
    List<Learning> findAllById(@Param("ids") List<Long> ids);
}