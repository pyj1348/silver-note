package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import silver.silvernote.domain.MemberLearning;
import silver.silvernote.domain.member.Member;

import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.util.List;

public interface MemberLearningRepository extends JpaRepository<MemberLearning, Long> {

    List<MemberLearning> findAllByMemberAndDate(Member member, LocalDate date);

    @Modifying
    @Query(value = "delete from member_learning where (member_learning.member_id in :ids) " +
            "AND (member_learning.date BETWEEN :startDate AND :endDate)",
            nativeQuery = true)
    void deleteAllByDateBetween(@Param("ids") List<Long> ids,
                                @Param("startDate") LocalDate startDate,
                                @Param("endDate") LocalDate endDate);
}
