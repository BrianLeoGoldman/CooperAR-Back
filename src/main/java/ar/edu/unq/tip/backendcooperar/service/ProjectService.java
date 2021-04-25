package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.DTO.ProjectDTO;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.persistence.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

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
        projectRepository.deleteById(id);
    }


}
