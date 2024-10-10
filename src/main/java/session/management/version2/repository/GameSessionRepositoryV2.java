package session.management.version2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import session.management.version2.domain.Users;

public interface GameSessionRepositoryV2 extends JpaRepository<Users, Long> {

}
