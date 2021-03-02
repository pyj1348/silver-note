package silver.silvernote.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.member.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByName(String name);
    Optional<Employee> findByNameAndRrn(String name, String rrn);
}
