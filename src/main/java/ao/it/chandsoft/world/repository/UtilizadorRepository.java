package ao.it.chandsoft.world.repository;

import ao.it.chandsoft.world.domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilizadorRepository extends JpaRepository<UserModel, UUID> {

    Optional<UserModel> findByUsername(String username);
}
