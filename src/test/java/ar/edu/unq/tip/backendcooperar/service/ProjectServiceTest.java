package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.DTO.ProjectDTO;
import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.builder.ProjectBuilder;
import ar.edu.unq.tip.backendcooperar.model.builder.UserBuilder;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidProjectException;
import ar.edu.unq.tip.backendcooperar.persistence.ProjectRepository;
import ar.edu.unq.tip.backendcooperar.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ProjectServiceTest {

    @InjectMocks private ProjectService projectService;
    @Mock private ProjectRepository projectRepository;
    @Mock private UserService userService;
    @Mock private SendEmailService sendEmailService;

    @Test
    public void testProjectServiceFindById() throws DataNotFoundException {
        MockitoAnnotations.openMocks(this);
        Integer id = 9;
        Project project = ProjectBuilder.aProject().withName("A project").build();
        when(projectRepository.existsById(id)).thenReturn(true);
        when(projectRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(project));
        assertEquals(project, projectService.findById(id));
    }

    @Test
    public void testProjectServiceFindByIdForNonExistingTask() {
        MockitoAnnotations.openMocks(this);
        Integer id = 9;
        when(projectRepository.existsById(id)).thenReturn(false);
        try {
            projectService.findById(id);
        } catch (DataNotFoundException e) {
            String message = "EL PROYECTO " + id + " NO EXISTE";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testProjectServiceFindAll() {
        MockitoAnnotations.openMocks(this);
        String name_1 = "A project";
        String name_2 = "Another project";
        Project project_1 = ProjectBuilder.aProject().withName(name_1).build();
        Project project_2 = ProjectBuilder.aProject().withName(name_2).build();
        List<Project> recoveredProjects = new ArrayList<>();
        recoveredProjects.add(project_1);
        recoveredProjects.add(project_2);
        when(projectRepository.findAll()).thenReturn(recoveredProjects);
        List<ProjectDTO> recoveredProjectDTOs = projectService.findAll();
        assertEquals(2, recoveredProjectDTOs.size());
        assertEquals(name_1, recoveredProjectDTOs.get(0).getName());
        assertEquals(name_2, recoveredProjectDTOs.get(1).getName());
    }

    @Test
    public void testProjectServiceCreateProject() throws InvalidProjectException, DataNotFoundException {
        MockitoAnnotations.openMocks(this);
        String name = "Project name";
        String budget = "1230";
        String description = "This is a test project";
        String category = "MINERIA";
        String owner = "juan123";
        User user = UserBuilder.aUser().withNickname(owner).withMoney(BigDecimal.valueOf(7000)).build();
        when(userService.findById(owner)).thenReturn(user);
        Project newProject = projectService.createProject(name, budget, description, category, owner);
        assertEquals(name, newProject.getName());
        assertEquals(description, newProject.getDescription());
    }

}