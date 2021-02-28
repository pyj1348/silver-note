package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.CenterLearning;
import silver.silvernote.domain.Learning;

import java.time.LocalDate;
import java.util.Optional;

public interface CenterLearningRepository extends JpaRepository<CenterLearning, Long> {

    Optional<CenterLearning> findByDate(LocalDate date);

}
