package ar.edu.unq.tip.backendcooperar.persistence;

import ar.edu.unq.tip.backendcooperar.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer>  {

    @Query("SELECT m FROM Message m WHERE m.taskId=?1")
    Iterable<Message> findMessagesFromTask(Integer id);

}
