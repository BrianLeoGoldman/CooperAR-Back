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
import java.util.ArrayList;
import java.util.List;

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
        String userName = "Roberto";
        User user = UserBuilder.aUser()
                .withNickname(userName)
                .withEmail("roberto@mail.com")
                .build();
        Task task1 = TaskBuilder.aTask()
                .withName("Prueba")
                .withDescription("Esta es la descripcion")
                .withReward(BigDecimal.valueOf(7560))
                .build();
        Task task2 = TaskBuilder.aTask()
                .withName("Otra Prueba")
                .withDescription("Esta es otra descripcion")
                .withReward(BigDecimal.valueOf(12))
                .build();
        Project project = ProjectBuilder.aProject()
                .withName("Necesito organizar una fiesta")
                .withBudget(BigDecimal.valueOf(12000))
                .build();
        project.addTask(task1);
        project.addTask(task2);
        user.addProject(project);
        userRepository.save(user);


    }
}
