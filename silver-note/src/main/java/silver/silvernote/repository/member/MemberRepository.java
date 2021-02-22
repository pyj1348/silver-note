package silver.silvernote.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.member.Member;
import silver.silvernote.domain.member.JoinStatus;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);
    List<Member> findMembersByStatus(JoinStatus joinStatus);
    Optional<Member> findByNameAndRrn(String name, String rrn);

}
