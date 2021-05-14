package ar.edu.unq.tip.backendcooperar.persistence;

import ar.edu.unq.tip.backendcooperar.model.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {

    @EntityGraph(attributePaths = {"tasks"})
    Optional<Project> findById(Integer id);
}
