package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.builder.ProjectBuilder;
import ar.edu.unq.tip.backendcooperar.model.builder.TaskBuilder;
import ar.edu.unq.tip.backendcooperar.model.builder.UserBuilder;
import ar.edu.unq.tip.backendcooperar.persistence.ProjectRepository;
import ar.edu.unq.tip.backendcooperar.persistence.TaskRepository;
import ar.edu.unq.tip.backendcooperar.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Service
@Transactional
public class InitMemoryService {

    //@Autowired
    //private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initialize() {
        fireInitialData();
    }

    private void fireInitialData() {

        User user1 = UserBuilder.aUser().withNickname("robert1972").withEmail("roberto@mail.com").build();
        User user2 = UserBuilder.aUser().withNickname("mmm2000").withEmail("maria@mail.com").build();
        User user3 = UserBuilder.aUser().withNickname("tommy_abc").withEmail("tomas@mail.com").build();
        User user4 = UserBuilder.aUser().withNickname("xyz_998877").withEmail("xyz@mail.com").build();

        Project project1 = user1.createProject("ProjectAlpha", BigDecimal.valueOf(12000), "Description A");
        Project project2 = user1.createProject("ProjectBetha", BigDecimal.valueOf(3000), "Description B");
        Project project3 = user2.createProject("ProjectGamma", BigDecimal.valueOf(120700000), "Description C");

        Task task1 = project1.createTask("Task 110", "Cook a cake", BigDecimal.valueOf(390));
        Task task2 = project1.createTask("Task 220", "Take a photo", BigDecimal.valueOf(100));
        Task task3 = project2.createTask("Task 330", "Build a house", BigDecimal.valueOf(1200));
        Task task4 = project2.createTask("Task 440", "Chase a mouse", BigDecimal.valueOf(600));
        Task task5 = project3.createTask("Task 550", "Eat a bug", BigDecimal.valueOf(222));
        Task task6 = project3.createTask("Task 660", "Send a letter", BigDecimal.valueOf(890));
        Task task7 = project1.createTask("Task 770", "Kill yourself", BigDecimal.valueOf(566));

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);


    }
}
