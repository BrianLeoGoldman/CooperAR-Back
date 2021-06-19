package ar.edu.unq.tip.backendcooperar.persistence;

import ar.edu.unq.tip.backendcooperar.model.MoneyRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyRequestRepository extends CrudRepository<MoneyRequest, Integer> {

    @Query("SELECT r FROM MoneyRequest r WHERE r.state=?1")
    Iterable<MoneyRequest> findAllByState(String state);

}
