package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.DTO.ProjectDTO;
import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidTaskException;
import ar.edu.unq.tip.backendcooperar.persistence.ProjectRepository;
import ar.edu.unq.tip.backendcooperar.persistence.TaskRepository;
import ar.edu.unq.tip.backendcooperar.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Project findById(Integer id) throws DataNotFoundException {
        if(projectRepository.existsById(id)){
            return projectRepository.findById(id).get();
        }
        else {
            throw new DataNotFoundException("Project " + id + " does not exists");
        }
    }

    public List<ProjectDTO> findAll(){
        /*return projectRepository.findAll();*/
        List<Project> projects = new ArrayList<>();
        this.projectRepository.findAll().forEach(projects::add);
        return projects.stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

    public void addNewProject(Project project){
        this.projectRepository.save(project);
    }

    // TODO: this method doesn't update, it creates a new project
    public void updateProject(Project project){
        this.projectRepository.save(project);
    }

    public void deleteProject(Integer id){
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        }
    }


    public Task createTask(String name, String reward, String description, String projectId, String difficulty, String owner) throws InvalidTaskException {
        Task task = null;
        if (projectRepository.existsById(Integer.valueOf(projectId))) {
            Project project = projectRepository.findById(Integer.valueOf(projectId)).get();
            task = project.createTask(name, description, BigDecimal.valueOf(Integer.parseInt(reward)), difficulty);
            projectRepository.save(project);
            return task;
        }
        return task;
    }
}
