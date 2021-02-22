package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.Learning;
import silver.silvernote.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

}
