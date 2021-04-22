package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.persistence.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public void addNewProject(Project project){
        this.projectRepository.save(project);
    }

    // TODO: this method doesn't update, it creates a new project
    public void updateProject(Project project){
        this.projectRepository.save(project);
    }

    public void deleteProject(Integer id){
        projectRepository.deleteById(id);
    }

    public Optional<Project> getProject(Integer id){
        return projectRepository.findById(id);
    }

    public Iterable<Project> getAllProjects(){
        return projectRepository.findAll();
    }
}
