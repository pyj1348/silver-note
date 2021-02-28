package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.MemberLearning;

import java.time.LocalDate;
import java.util.Optional;

public interface MemberLearningRepository extends JpaRepository<MemberLearning, Long> {

}
