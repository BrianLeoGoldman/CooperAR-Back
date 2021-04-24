package ar.edu.unq.tip.backendcooperar.persistence;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {

    //public List<Project> findByDescription(String description);

    @EntityGraph(attributePaths = {"tasks"})
    Optional<Project> findById(Integer id);
}
