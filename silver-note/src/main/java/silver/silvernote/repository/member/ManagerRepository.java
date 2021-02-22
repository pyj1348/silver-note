package silver.silvernote.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.member.JoinStatus;
import silver.silvernote.domain.member.Manager;
import silver.silvernote.domain.member.Member;
import silver.silvernote.domain.member.PermissionStatus;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByName(String name);
    Optional<Manager> findByNameAndRrn(String name, String rrn);
    List<Manager> findManagersByPermission(PermissionStatus permission);
}
