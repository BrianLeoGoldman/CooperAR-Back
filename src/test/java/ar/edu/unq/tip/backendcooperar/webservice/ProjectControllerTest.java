package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.DTO.ProjectDTO;
import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.builder.ProjectBuilder;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidProjectException;
import ar.edu.unq.tip.backendcooperar.service.FileService;
import ar.edu.unq.tip.backendcooperar.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectController.class)
@SuppressWarnings("unchecked")
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProjectService projectService;
    @MockBean
    private FileService fileService;
    @Autowired
    private ProjectController projectController;

    @Test
    public void testGetAllProjectsMethod() {
        Project project = ProjectBuilder.aProject().build();
        ProjectDTO projectDTO = new ProjectDTO(project);
        List<ProjectDTO> allProjectsDTO = Arrays.asList(projectDTO);
        when(projectService.findAll()).thenReturn(allProjectsDTO);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) projectController.getAllProjects();
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
        assertEquals(allProjectsDTO, httpResponse.getBody());
    }

    @Test
    public void testGetAllProjectsRequest() throws Exception {

        Project project = ProjectBuilder.aProject().build();
        ProjectDTO projectDTO = new ProjectDTO(project);
        List<ProjectDTO> allProjectsDTO = Arrays.asList(projectDTO);
        given(projectService.findAll()).willReturn(allProjectsDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/project")
                .header("Authorization", UserController.getJWTToken("default_user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(projectDTO.getName())))
                .andExpect(jsonPath("$[0].description", is(projectDTO.getDescription())));
    }

    @Test
    public void testGetProjectMethod() throws DataNotFoundException {
        Project project = ProjectBuilder.aProject().build();
        Integer id = 3;
        when(projectService.findProjectWithFiles(id)).thenReturn(project);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) projectController.getProject(id);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
        assertEquals(project, httpResponse.getBody());
    }

    @Test
    public void testGetProjectRequest() throws Exception {
        Project project = ProjectBuilder.aProject().build();
        Integer id = 3;
        given(projectService.findProjectWithFiles(id)).willReturn(project);

        mockMvc.perform(MockMvcRequestBuilders.get("/project/" + id)
                .header("Authorization", UserController.getJWTToken("default_user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.*", hasSize(11)))
                .andExpect(jsonPath("$.name", is(project.getName())))
                .andExpect(jsonPath("$.description", is(project.getDescription())));
    }

    @Test
    public void testCreateProjectRequest() throws Exception, InvalidProjectException {
        String name = "project_name";
        String budget = "1230";
        String description = "project_description";
        String category = "ADMINISTRACION";
        String owner = "juan123";
        Project project = ProjectBuilder.aProject()
                .withName(name)
                .withBudget(BigDecimal.valueOf(Integer.parseInt(budget)))
                .withDescription(description)
                .withCategory(category)
                .withOwner(owner)
                .build();
        given(projectService.createProject(name, budget, description, category, owner)).willReturn(project);

        mockMvc.perform(MockMvcRequestBuilders.put("/project?name=" + name +
                "&budget=" + budget + "&description=" + description + "&category=" + category +
                "&owner=" + owner)
                .header("Authorization", UserController.getJWTToken("default_user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.*", hasSize(11)))
                .andExpect(jsonPath("$.name", is(project.getName())))
                .andExpect(jsonPath("$.description", is(project.getDescription())));
    }

}