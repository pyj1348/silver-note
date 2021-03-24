package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.Admin;
import silver.silvernote.domain.Album;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByLoginId(String loginId);
    Optional<Admin> findByPassword(String password);
    Optional<Admin> findByLoginIdAndPassword(String loginID, String password);

}
