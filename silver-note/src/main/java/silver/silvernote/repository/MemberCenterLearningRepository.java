package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.CenterLearning;
import silver.silvernote.domain.MemberCenterLearning;

import java.time.LocalDate;
import java.util.Optional;

public interface MemberCenterLearningRepository extends JpaRepository<MemberCenterLearning, Long> {

    Optional<MemberCenterLearning> findByDate(LocalDate date);

}
