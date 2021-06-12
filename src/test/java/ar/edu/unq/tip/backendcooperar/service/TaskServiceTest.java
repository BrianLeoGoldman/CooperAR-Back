package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.builder.ProjectBuilder;
import ar.edu.unq.tip.backendcooperar.model.builder.TaskBuilder;
import ar.edu.unq.tip.backendcooperar.model.builder.UserBuilder;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidTaskException;
import ar.edu.unq.tip.backendcooperar.persistence.ProjectRepository;
import ar.edu.unq.tip.backendcooperar.persistence.TaskRepository;
import ar.edu.unq.tip.backendcooperar.persistence.UserRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SendEmailService sendEmailService;

    @Test
    public void testTaskServiceFindAll() {
        MockitoAnnotations.openMocks(this);
        List<Task> tasks = new ArrayList<>();
        tasks.add(TaskBuilder.aTask().withName("Tarea de prueba 1").build());
        tasks.add(TaskBuilder.aTask().withName("Tarea de prueba 2").build());
        when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> recoveredTasks = taskService.findAll();
        assertEquals(tasks, recoveredTasks);
        assertEquals(2, recoveredTasks.size());
    }

    @Test
    public void testTaskServiceFindById() throws DataNotFoundException {
        MockitoAnnotations.openMocks(this);
        String name = "Tarea de prueba 1";
        Integer id = 3;
        Task task = TaskBuilder.aTask().withName(name).build();
        when(taskRepository.existsById(id)).thenReturn(true);
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        assertEquals(task, taskService.findById(id));
    }

    @Test
    public void testTaskServiceFindByIdForNonExistingTask() {
        MockitoAnnotations.openMocks(this);
        String name = "Tarea de prueba 1";
        Integer id = 3;
        when(taskRepository.existsById(id)).thenReturn(false);
        try {
            taskService.findById(id);
        } catch (DataNotFoundException e) {
            String message = "LA TAREA " + id + " NO EXISTE";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testTaskServiceCreateTask() throws InvalidTaskException {
        MockitoAnnotations.openMocks(this);
        Integer id = 3;
        Project project = ProjectBuilder.aProject().withBudget(BigDecimal.valueOf(5000)).build();
        String name = "Tarea de prueba 1";
        String reward = "300";
        String description = "Descripcion de la tarea";
        Integer projectId = 7;
        String difficulty = "FACIL";
        String owner = "juan123";
        User user = UserBuilder.aUser().withNickname(owner).build();
        when(projectRepository.existsById(projectId)).thenReturn(true);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        //doNothing().when(projectRepository).save(project);
        when(userRepository.findByNickname(owner)).thenReturn(Optional.of(user));
        //doNothing().when(sendEmailService).sendSimpleMessage(user.getEmail(), anyString(), anyString());
        Task newTask = taskService.createTask(name, reward, description, projectId.toString(), difficulty, owner);
        assertEquals(name, newTask.getName());
        assertEquals(description, newTask.getDescription());
    }

}