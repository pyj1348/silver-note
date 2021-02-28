package silver.silvernote.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.member.Manager;
import silver.silvernote.domain.member.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByName(String name);
    Optional<Patient> findByNameAndRrn(String name, String rrn);
    List<Patient> findAllByCenterId(Long centerId);
}
