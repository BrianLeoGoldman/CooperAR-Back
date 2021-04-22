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

        User user1 = UserBuilder.aUser().withNickname("robert1985").withEmail("roberto@mail.com").build();
        User user2 = UserBuilder.aUser().withNickname("maryjane").withEmail("mj@mail.com").build();
        User user3 = UserBuilder.aUser().withNickname("thomas_carlson").withEmail("thom_car@mail.com").build();
        User user4 = UserBuilder.aUser().withNickname("abel1945").withEmail("abel1945@mail.com").build();

        Project project1 = user1.createProject("Start a new company", BigDecimal.valueOf(12000), "I want to start a new company that sells fashion products");
        Project project2 = user2.createProject("Assistance required at my hotel", BigDecimal.valueOf(7000), "My hotel is currently low on staff");
        Project project3 = user3.createProject("Software company expanding", BigDecimal.valueOf(9000), "My software company is expanding to new markets");
        Project project4 = user4.createProject("I am old and need help", BigDecimal.valueOf(1200), "I am an old person and I need help with various tasks");
        Project project5 = user1.createProject("Fix building issues", BigDecimal.valueOf(8300), "The building I just bought need repairs");
        Project project6 = user2.createProject("Kitchen work", BigDecimal.valueOf(2900), "I need cookers with experience");

        Task task1 = project3.createTask("Code a Python application", "I need software developers", BigDecimal.valueOf(380));
        Task task2 = project2.createTask("Fix a wooden door", "My front door is broken", BigDecimal.valueOf(200));
        Task task3 = project4.createTask("Cook a chocolate cake", "I am hungry and I like chocolate", BigDecimal.valueOf(80));
        Task task4 = project2.createTask("Clean a hotel", "I am expecting hosts soon", BigDecimal.valueOf(270));
        Task task5 = project1.createTask("Code a website", "I need a website for my store", BigDecimal.valueOf(300));
        Task task6 = project4.createTask("Fix a pair of shoes", "Its the only pair I have for work!", BigDecimal.valueOf(130));
        Task task7 = project5.createTask("Repair a meeting room", "I already have all the materials", BigDecimal.valueOf(2300));
        Task task8 = project4.createTask("Chase a mouse", "There is a mouse in my house!", BigDecimal.valueOf(50));
        Task task9 = project1.createTask("Design an advertising campaign", "I want to sell my new product", BigDecimal.valueOf(240));
        Task task10 = project2.createTask("Attend a bar", "I need a barman at my bar", BigDecimal.valueOf(210));
        Task task11 = project2.createTask("Guard a building", "Need someone with security experience", BigDecimal.valueOf(300));
        Task task12 = project4.createTask("Pick up a package", "I cannot travel myself", BigDecimal.valueOf(110));
        Task task13 = project1.createTask("Sell clothes", "I need a salesman", BigDecimal.valueOf(225));
        Task task14 = project5.createTask("Fix gas pipe", "A broken pipe is dangerous and is losing gas", BigDecimal.valueOf(1700));
        Task task15 = project5.createTask("Clean offices", "Several offices are dirty", BigDecimal.valueOf(700));
        Task task16 = project6.createTask("Cook meals for 40 people", "Guests must be served", BigDecimal.valueOf(2760));


        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);


    }
}
