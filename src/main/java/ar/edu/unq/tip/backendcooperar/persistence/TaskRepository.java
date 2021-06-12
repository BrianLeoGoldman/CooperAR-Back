package ar.edu.unq.tip.backendcooperar.persistence;

import ar.edu.unq.tip.backendcooperar.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {

    @Query("SELECT t FROM Task t WHERE t.worker=?1")
    Iterable<Task> findAssignedTasks(String nickname);
    // TODO: should we look for task only with status ASIGNADA?

}
