package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.builder.ProjectBuilder;
import ar.edu.unq.tip.backendcooperar.model.builder.TaskBuilder;
import ar.edu.unq.tip.backendcooperar.model.builder.UserBuilder;
import ar.edu.unq.tip.backendcooperar.model.enums.TaskState;
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

    @InjectMocks private TaskService taskService;
    @Mock private TaskRepository taskRepository;
    @Mock private ProjectService projectService;
    @Mock private UserService userService;
    @Mock private SendEmailService sendEmailService;

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
    public void testTaskServiceFindAssignedTasks() {
        MockitoAnnotations.openMocks(this);
        List<Task> tasks = new ArrayList<>();
        String worker = "maria_ana";
        tasks.add(TaskBuilder.aTask().withName("Tarea 1").withState(TaskState.EN_CURSO.name()).withWorker(worker).build());
        tasks.add(TaskBuilder.aTask().withName("Tarea 2").withState(TaskState.EN_CURSO.name()).withWorker(worker).build());
        when(taskRepository.findAssignedTasks(worker)).thenReturn(tasks);
        List<Task> recoveredTasks = taskService.findAssignedTasks(worker);
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
    public void testTaskServiceCreateTask() throws InvalidTaskException, DataNotFoundException {
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
        when(projectService.findById(projectId)).thenReturn(project);
        //doNothing().when(projectRepository).save(project);
        when(userService.findById(owner)).thenReturn(user);
        //doNothing().when(sendEmailService).sendSimpleMessage(user.getEmail(), anyString(), anyString());
        Task newTask = taskService.createTask(name, reward, description, projectId.toString(), difficulty, owner);
        assertEquals(name, newTask.getName());
        assertEquals(description, newTask.getDescription());
    }

    @Test
    public void testTaskServiceAssignWorker() throws DataNotFoundException {
        MockitoAnnotations.openMocks(this);
        String id = "7";
        String nickname = "juan123";
        String owner = "abel789";
        Task task = TaskBuilder.aTask()
                .withName("A task")
                .withState(TaskState.DISPONIBLE.name())
                .withOwner(owner)
                .withWorker("SIN TRABAJADOR")
                .build();
        User user = UserBuilder.aUser().build();
        when(taskRepository.existsById(Integer.valueOf(id))).thenReturn(true);
        /*when(userRepository.existsById(nickname)).thenReturn(true);*/
        when(taskRepository.findById(Integer.valueOf(id))).thenReturn(Optional.ofNullable(task));
        when(userService.findById(task.getOwner())).thenReturn(user);
        taskService.assignWorker(nickname, id);
        assertEquals(nickname, task.getWorker());
        assertEquals(TaskState.EN_CURSO.name(), task.getState());
    }

    @Test
    public void testTaskServiceUnassignWorker() throws InvalidTaskException, DataNotFoundException {
        MockitoAnnotations.openMocks(this);
        String id = "7";
        String owner = "juan123";
        String worker = "abel789";
        Task task = TaskBuilder.aTask()
                .withName("A task")
                .withState(TaskState.DISPONIBLE.name())
                .withOwner(owner)
                .withWorker(worker)
                .build();
        User user = UserBuilder.aUser().build();
        when(taskRepository.existsById(Integer.valueOf(id))).thenReturn(true);
        when(taskRepository.findById(Integer.valueOf(id))).thenReturn(Optional.ofNullable(task));
        when(userService.findById(task.getWorker())).thenReturn(user);
        taskService.unassignWorker(id);
        assertEquals("SIN TRABAJADOR", task.getWorker());
        assertEquals(TaskState.DISPONIBLE.name(), task.getState());
    }

    @Test
    public void testTaskServiceCompleteTask() throws DataNotFoundException {
        MockitoAnnotations.openMocks(this);
        String id = "7";
        String owner = "juan123";
        String worker = "abel789";
        Task task = TaskBuilder.aTask()
                .withName("A task")
                .withState(TaskState.DISPONIBLE.name())
                .withOwner(owner)
                .withWorker(worker)
                .build();
        User user = UserBuilder.aUser().build();
        when(taskRepository.existsById(Integer.valueOf(id))).thenReturn(true);
        when(taskRepository.findById(Integer.valueOf(id))).thenReturn(Optional.ofNullable(task));
        when(userService.findById(task.getOwner())).thenReturn(user);
        taskService.completeTask(id);
        assertEquals(worker, task.getWorker());
        assertEquals(TaskState.COMPLETA.name(), task.getState());
    }

    @Test
    public void testTaskServiceApproveTask() throws DataNotFoundException {
        MockitoAnnotations.openMocks(this);
        String id = "7";
        String owner = "juan123";
        String workerNickname = "abel789";
        Integer projectId = 34;
        BigDecimal initialMoney = BigDecimal.valueOf(3000);
        BigDecimal reward = BigDecimal.valueOf(2500);
        Task task = TaskBuilder.aTask()
                .withName("A task")
                .withState(TaskState.DISPONIBLE.name())
                .withOwner(owner)
                .withWorker(workerNickname)
                .withProjectId(projectId)
                .withReward(reward)
                .build();
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        User worker = UserBuilder.aUser().withNickname(workerNickname).withMoney(initialMoney).build();
        Project project = ProjectBuilder.aProject()
                .withName("Project")
                .withOwner(owner)
                .withTasks(taskList)
                .build();
        when(taskRepository.existsById(Integer.valueOf(id))).thenReturn(true);
        when(taskRepository.findById(Integer.valueOf(id))).thenReturn(Optional.ofNullable(task));
        /*when(userService.existsById(workerNickname)).thenReturn(true);*/
        when(userService.findById(task.getWorker())).thenReturn(worker);
        when(projectService.findById(projectId)).thenReturn(project);
        taskService.approveTask(id);
        assertEquals(workerNickname, task.getWorker());
        assertEquals(TaskState.FINALIZADA.name(), task.getState());
        assertEquals(initialMoney.add(reward), worker.getMoney());
    }

    @Test
    public void testTaskServiceUnapproveTask() throws DataNotFoundException {
        MockitoAnnotations.openMocks(this);
        String id = "7";
        String owner = "juan123";
        String workerNickname = "abel789";
        Task task = TaskBuilder.aTask()
                .withName("A task")
                .withState(TaskState.DISPONIBLE.name())
                .withOwner(owner)
                .withWorker(workerNickname)
                .build();
        User worker = UserBuilder.aUser().withNickname(workerNickname).build();
        when(taskRepository.existsById(Integer.valueOf(id))).thenReturn(true);
        when(taskRepository.findById(Integer.valueOf(id))).thenReturn(Optional.ofNullable(task));
        when(userService.findById(task.getWorker())).thenReturn(worker);
        taskService.unapproveTask(id);
        assertEquals(workerNickname, task.getWorker());
        assertEquals(TaskState.EN_CURSO.name(), task.getState());
    }

    @Test
    public void testTaskServiceCancelTask() throws DataNotFoundException {
        MockitoAnnotations.openMocks(this);
        String id = "7";
        String owner = "juan123";
        String workerNickname = "abel789";
        Task task = TaskBuilder.aTask()
                .withName("A task")
                .withState(TaskState.DISPONIBLE.name())
                .withOwner(owner)
                .withWorker(workerNickname)
                .build();
        when(taskRepository.existsById(Integer.valueOf(id))).thenReturn(true);
        when(taskRepository.findById(Integer.valueOf(id))).thenReturn(Optional.ofNullable(task));
        taskService.cancelTask(id);
        assertEquals("SIN TRABAJADOR", task.getWorker());
        assertEquals(TaskState.CANCELADA.name(), task.getState());
    }

}