package ar.edu.unq.tip.backendcooperar.persistence;

import ar.edu.unq.tip.backendcooperar.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {
}
