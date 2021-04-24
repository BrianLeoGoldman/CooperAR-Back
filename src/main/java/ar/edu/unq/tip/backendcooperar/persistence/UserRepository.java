package ar.edu.unq.tip.backendcooperar.persistence;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ar.edu.unq.tip.backendcooperar.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @EntityGraph(attributePaths = {"projects", "projects.tasks"})
    Optional<User> findByNickname(String id);


}
