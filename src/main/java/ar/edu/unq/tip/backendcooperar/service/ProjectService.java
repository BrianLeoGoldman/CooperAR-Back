package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.DTO.ProjectDTO;
import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidProjectException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidTaskException;
import ar.edu.unq.tip.backendcooperar.persistence.ProjectRepository;
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
    private UserRepository userRepository;

    public Project findById(Integer id) throws DataNotFoundException {
        if(projectRepository.existsById(id)){
            return projectRepository.findById(id).get();
        }
        else {
            throw new DataNotFoundException("EL PROYECTO " + id + " NO EXISTE");
        }
    }

    public List<ProjectDTO> findAll(){
        List<Project> projects = new ArrayList<>();
        this.projectRepository.findAll().forEach(projects::add);
        return projects.stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

    public Project createProject(String name, String budget, String description, String category, String owner) throws InvalidProjectException {
        if(userRepository.existsById(owner)){
            User user = userRepository.findByNickname(owner).get();
            Project project = user.createProject(name, BigDecimal.valueOf(Integer.parseInt(budget)), description, category);
            userRepository.save(user);
            return project;
        }
        else {
            throw new InvalidProjectException("EL PROYECTO NO PUDO SER CREADO PORUE EL USUARIO " + owner + " NO EXISTE");
        }
    }

    public void deleteProject(Integer id){
        if (projectRepository.existsById(id)) {
            // TODO: we should return budget to the owner money!!!
            projectRepository.deleteById(id);
        }
    }
}
