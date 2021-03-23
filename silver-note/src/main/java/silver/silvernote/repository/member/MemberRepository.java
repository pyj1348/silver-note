package silver.silvernote.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import silver.silvernote.domain.member.JoinStatus;
import silver.silvernote.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);
    List<Member> findMembersByStatus(JoinStatus joinStatus);
    Optional<Member> findByNameAndRrn(String name, String rrn);

    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByPassword(String password);

    Optional<Member> findByLoginIdAndPassword(String loginId, String password);

    @Query("SELECT m FROM Member m WHERE m.id in :ids")
    List<Member> findAllById(@Param("ids") List<Long> ids);


}
