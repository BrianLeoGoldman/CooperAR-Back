package ar.edu.unq.tip.backendcooperar.persistence;

import org.springframework.data.repository.CrudRepository;
import ar.edu.unq.tip.backendcooperar.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
