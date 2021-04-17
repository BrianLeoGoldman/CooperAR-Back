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

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initialize() {
        fireInitialData();
    }

    private void fireInitialData() {

        String nickname1 = "robert1972";
        User user1 = UserBuilder.aUser().withNickname(nickname1).withEmail("roberto@mail.com").build();
        String nickname2 = "mmm2000";
        User user2 = UserBuilder.aUser().withNickname(nickname2).withEmail("maria@mail.com").build();

        Task task1 = TaskBuilder.aTask().withName("Tarea A").withDescription("Desc Tarea A")
                .withReward(BigDecimal.valueOf(7560)).build();
        Task task2 = TaskBuilder.aTask().withName("Tarea B").withDescription("Desc Tarea B")
                .withReward(BigDecimal.valueOf(12)).build();

        Project project1 = ProjectBuilder.aProject().withName("Project A")
                .withDescription("Description 1").withBudget(BigDecimal.valueOf(12000)).build();
        Project project2 = ProjectBuilder.aProject().withName("Project B")
                .withDescription("Description 2").withBudget(BigDecimal.valueOf(45000)).build();
        Project project3 = ProjectBuilder.aProject().withName("Project C")
                .withDescription("Description 3").withBudget(BigDecimal.valueOf(1000)).build();

        project1.addTask(task1);
        project1.addTask(task2);
        user1.addProject(project1);
        user2.addProject(project2);
        user2.addProject(project3);
        userRepository.save(user1);
        userRepository.save(user2);


    }
}
