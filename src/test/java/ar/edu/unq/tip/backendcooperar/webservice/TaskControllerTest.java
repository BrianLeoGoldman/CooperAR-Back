package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.builder.TaskBuilder;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidTaskException;
import ar.edu.unq.tip.backendcooperar.service.FileService;
import ar.edu.unq.tip.backendcooperar.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
@SuppressWarnings("unchecked")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskService taskService;
    @MockBean
    private FileService fileService;
    @Autowired
    private TaskController taskController;

    @Test
    public void testGetAllTasksMethod() {
        Task task = TaskBuilder.aTask().build();
        List<Task> allTasks = Arrays.asList(task);
        when(taskService.findAll()).thenReturn(allTasks);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) taskController.getAllTasks();
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
        assertEquals(allTasks, httpResponse.getBody());
    }

    @Test
    public void testGetAllTasksRequest() throws Exception {

        Task task = TaskBuilder.aTask().build();
        List<Task> allTasks = Arrays.asList(task);
        given(taskService.findAll()).willReturn(allTasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/task")
                .header("Authorization", UserController.getJWTToken("default_user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(task.getName())))
                .andExpect(jsonPath("$[0].description", is(task.getDescription())));
    }

    @Test
    public void testGetTaskMethod() throws DataNotFoundException {
        Integer id = 1;
        Task task = TaskBuilder.aTask().build();
        when(taskService.findTaskWithFiles(id)).thenReturn(task);
        ResponseEntity<Task> httpResponse = (ResponseEntity<Task>) taskController.getTask(id);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
        assertEquals(task, httpResponse.getBody());
    }

    @Test
    public void testGetTaskRequest() throws Exception {
        Integer id = 1;
        Task task = TaskBuilder.aTask().build();
        given(taskService.findTaskWithFiles(id)).willReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.get("/task/" + id)
                .header("Authorization", UserController.getJWTToken("default_user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.*", hasSize(12)))
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())));
    }

    @Test
    public void testCreateTaskMethod() throws InvalidTaskException, DataNotFoundException {
        String name = "task_name";
        String reward = "230";
        String description = "task_description";
        String projectId = "12";
        String difficulty = "REGULAR";
        String owner = "juan123";
        Task task = TaskBuilder.aTask()
                .withName(name)
                .withReward(BigDecimal.valueOf(Integer.parseInt(reward)))
                .withDescription(description)
                .withProjectId(Integer.parseInt(projectId))
                .withDifficulty(difficulty)
                .withOwner(owner)
                .build();
        when(taskService.createTask(name, reward, description, projectId, difficulty, owner)).thenReturn(task);
        ResponseEntity<Task> httpResponse = (ResponseEntity<Task>) taskController.createTask(name, reward, description, projectId, difficulty, owner);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
        assertEquals(task, httpResponse.getBody());
    }

    @Test
    public void testCreateTaskRequest() throws Exception, InvalidTaskException {
        String name = "task_name";
        String reward = "230";
        String description = "task_description";
        String projectId = "12";
        String difficulty = "REGULAR";
        String owner = "juan123";
        Task task = TaskBuilder.aTask()
                .withName(name)
                .withReward(BigDecimal.valueOf(Integer.parseInt(reward)))
                .withDescription(description)
                .withProjectId(Integer.parseInt(projectId))
                .withDifficulty(difficulty)
                .withOwner(owner)
                .build();
        given(taskService.createTask(name, reward, description, projectId, difficulty, owner)).willReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.put("/task?name=" + name +
                "&reward=" + reward + "&description=" + description + "&projectId=" + projectId +
                "&difficulty=" + difficulty + "&owner=" + owner)
                .header("Authorization", UserController.getJWTToken("default_user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.*", hasSize(12)))
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())));
    }

    @Test
    public void testAssignWorkerMethod() throws DataNotFoundException {
        String user = "juan123";
        String id = "1";
        doNothing().when(taskService).assignWorker(user, id);
        ResponseEntity<Task> httpResponse = (ResponseEntity<Task>) taskController.assignWorker(user, id);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
        assertNull(httpResponse.getBody());
    }

    @Test
    public void testAssignWorkerMethodWithError() {
        String user = "juan123";
        String id = "1";
        String message = "EL USUARIO " + user + " O LA TAREA NO EXISTEN";
        try {
            Mockito.doThrow(new DataNotFoundException(message)).when(taskService).assignWorker(user, id);
            taskService.assignWorker(user, id);
        } catch (DataNotFoundException e) {
            ResponseEntity<Task> httpResponse = (ResponseEntity<Task>) taskController.assignWorker(user, id);
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
            assertNotNull(httpResponse.getBody());
            assertEquals("NO SE PUDO ASIGNAR EL USUARIO A LA TAREA: " + message, httpResponse.getBody());
        }
    }

    @Test
    public void testUnassignWorkerMethod() throws InvalidTaskException, DataNotFoundException {
        String id = "1";
        doNothing().when(taskService).unassignWorker(id);
        ResponseEntity<Task> httpResponse = (ResponseEntity<Task>) taskController.unassignWorker(id);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
        assertNull(httpResponse.getBody());
    }

    @Test
    public void testUnassignWorkerMethodWithError() throws InvalidTaskException {
        String id = "1";
        String message = "LA TAREA " + id + " NO EXISTE";
        try {
            Mockito.doThrow(new DataNotFoundException(message)).when(taskService).unassignWorker(id);
            taskService.unassignWorker(id);
        } catch (DataNotFoundException e) {
            ResponseEntity<Task> httpResponse = (ResponseEntity<Task>) taskController.unassignWorker(id);
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
            assertNotNull(httpResponse.getBody());
            assertEquals("NO SE PUDO DESASIGNAR EL USUARIO A LA TAREA: " + message, httpResponse.getBody());
        }
    }

    @Test
    public void testCompleteTaskMethod() throws DataNotFoundException {
        String id = "1";
        doNothing().when(taskService).completeTask(id);
        ResponseEntity<Task> httpResponse = (ResponseEntity<Task>) taskController.completeTask(id);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
        assertNull(httpResponse.getBody());
    }

    @Test
    public void testCompleteTaskMethodWithError() {
        String id = "1";
        String message = "LA TAREA " + id + " NO EXISTE";
        try {
            Mockito.doThrow(new DataNotFoundException(message)).when(taskService).completeTask(id);
            taskService.completeTask(id);
        } catch (DataNotFoundException e) {
            ResponseEntity<Task> httpResponse = (ResponseEntity<Task>) taskController.completeTask(id);
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
            assertNotNull(httpResponse.getBody());
            assertEquals("NO SE PUDO COMPLETAR LA TAREA: " + message, httpResponse.getBody());
        }
    }

    @Test
    public void testApproveTaskMethod() throws DataNotFoundException {
        String id = "1";
        doNothing().when(taskService).approveTask(id);
        ResponseEntity<Task> httpResponse = (ResponseEntity<Task>) taskController.approveTask(id);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
        assertNull(httpResponse.getBody());
    }

    @Test
    public void testApproveTaskMethodWithError() {
        String id = "1";
        String worker = "juan123";
        String message = "EL USUARIO " + worker + " NO EXISTE";
        try {
            Mockito.doThrow(new DataNotFoundException(message)).when(taskService).approveTask(id);
            taskService.approveTask(id);
        } catch (DataNotFoundException e) {
            ResponseEntity<Task> httpResponse = (ResponseEntity<Task>) taskController.approveTask(id);
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
            assertNotNull(httpResponse.getBody());
            assertEquals("NO SE PUDO APROBAR LA TAREA: " + message, httpResponse.getBody());
        }
    }

    @Test
    public void testUnapproveTaskMethod() throws DataNotFoundException {
        String id = "1";
        doNothing().when(taskService).unapproveTask(id);
        ResponseEntity<Task> httpResponse = (ResponseEntity<Task>) taskController.unapproveTask(id);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
        assertNull(httpResponse.getBody());
    }

    @Test
    public void testUnapproveTaskMethodWithError() {
        String id = "1";
        String message = "LA TAREA " + id + " NO EXISTE";
        try {
            Mockito.doThrow(new DataNotFoundException(message)).when(taskService).unapproveTask(id);
            taskService.unapproveTask(id);
        } catch (DataNotFoundException e) {
            ResponseEntity<Task> httpResponse = (ResponseEntity<Task>) taskController.unapproveTask(id);
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
            assertNotNull(httpResponse.getBody());
            assertEquals("NO SE PUDO DESAPROBAR LA TAREA: " + message, httpResponse.getBody());
        }
    }

}