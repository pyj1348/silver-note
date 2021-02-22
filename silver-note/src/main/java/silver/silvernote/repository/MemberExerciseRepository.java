package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.MemberCenterLearning;
import silver.silvernote.domain.MemberExercise;

import java.time.LocalDate;
import java.util.Optional;

public interface MemberExerciseRepository extends JpaRepository<MemberExercise, Long> {

    Optional<MemberExercise> findByDate(LocalDate date);

}
