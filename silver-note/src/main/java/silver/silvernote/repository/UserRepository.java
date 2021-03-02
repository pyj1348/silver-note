package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
