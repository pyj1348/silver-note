package silver.silvernote.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.member.Family;

import java.util.List;
import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Long> {

    Optional<Family> findByName(String name);
    Optional<Family> findByNameAndRrn(String name, String rrn);

    List<Family> findAllByCenterId(Long centerId);
}
