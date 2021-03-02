package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.MemberLearning;

public interface MemberLearningRepository extends JpaRepository<MemberLearning, Long> {

}
