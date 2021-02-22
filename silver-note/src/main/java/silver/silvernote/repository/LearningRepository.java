package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.Learning;

import java.util.Optional;

public interface LearningRepository extends JpaRepository<Learning, Long> {

    Optional<Learning> findByName(String name);
}
