package ar.edu.unq.tip.backendcooperar.persistence;

import ar.edu.unq.tip.backendcooperar.model.MoneyRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyRequestRepository extends CrudRepository<MoneyRequest, Integer> {
}
