package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.builder.TaskBuilder;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidTaskException;
import ar.edu.unq.tip.backendcooperar.service.TaskService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
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
    private TaskService service;
    @Autowired
    private TaskController taskController;

    @Test
    public void testGetAllTasksMethod() {
        Task task = TaskBuilder.aTask().build();
        List<Task> allTasks = Arrays.asList(task);
        when(service.findAll()).thenReturn(allTasks);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) taskController.getAllTasks();
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
        assertEquals(allTasks, httpResponse.getBody());
    }

    @Test
    public void testGetAllTasksRequest() throws Exception {

        Task task = TaskBuilder.aTask().build();
        List<Task> allTasks = Arrays.asList(task);
        given(service.findAll()).willReturn(allTasks);

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
        when(service.findById(id)).thenReturn(task);
        ResponseEntity<Task> httpResponse = (ResponseEntity<Task>) taskController.getTask(id);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
        assertEquals(task, httpResponse.getBody());
    }

    @Test
    public void testGetTaskRequest() throws Exception {
        Integer id = 1;
        Task task = TaskBuilder.aTask().build();
        given(service.findById(id)).willReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.get("/task/" + id)
                .header("Authorization", UserController.getJWTToken("default_user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.*", hasSize(12)))
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())));
    }

    @Test
    public void testCreateTaskMethod() throws InvalidTaskException {
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
        when(service.createTask(name, reward, description, projectId, difficulty, owner)).thenReturn(task);
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
        given(service.createTask(name, reward, description, projectId, difficulty, owner)).willReturn(task);

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
    public void testAssignWorkerMethod() throws InvalidTaskException {
        String user = "juan123";
        String id = "1";
        doNothing().when(service).assignWorker(user, id);
        ResponseEntity<Task> httpResponse = (ResponseEntity<Task>) taskController.assignWorker(user, id);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
        assertNull(httpResponse.getBody());
    }

}