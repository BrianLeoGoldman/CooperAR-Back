package ar.edu.unq.tip.backendcooperar.persistence;

import org.springframework.data.repository.CrudRepository;
import ar.edu.unq.tip.backendcooperar.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
