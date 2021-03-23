package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import silver.silvernote.domain.MemberLearning;

import java.time.LocalDate;
import java.util.List;

public interface MemberLearningRepository extends JpaRepository<MemberLearning, Long> {

    @Query(value = "SELECT * from member_learning join learning on member_learning.learning_id = learning.learning_id " +
            "where member_learning.member_id = :memberId " +
            "AND (member_learning.date BETWEEN :startDate AND :endDate)",
            nativeQuery = true)
    List<MemberLearning> findAllByMemberAndDate(@Param("memberId") Long memberId,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);

    @Modifying
    @Query(value = "delete from member_learning where (member_learning.member_id in :ids) " +
            "AND (member_learning.date BETWEEN :startDate AND :endDate)",
            nativeQuery = true)
    void deleteAllByDateBetween(@Param("ids") List<Long> ids,
                                @Param("startDate") LocalDate startDate,
                                @Param("endDate") LocalDate endDate);
}
