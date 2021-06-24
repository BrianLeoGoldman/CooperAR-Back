package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.DTO.ProjectDTO;
import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidProjectException;
import ar.edu.unq.tip.backendcooperar.persistence.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired private ProjectRepository projectRepository;
    @Autowired private UserService userService;
    @Autowired private FileService fileService;
    @Autowired private SendEmailService sendEmailService;

    public Project findById(Integer id) throws DataNotFoundException {
        if(projectRepository.existsById(id)){
            return projectRepository.findById(id).get();
        }
        else {
            throw new DataNotFoundException("EL PROYECTO " + id + " NO EXISTE");
        }
    }

    public Project findProjectWithFiles(Integer id) throws DataNotFoundException {
        Project project = findById(id);
        File[] listOfFiles = fileService.getFilesFromDirectory("src/main/resources/project/" + id + "/");
        if(listOfFiles != null){
            project.setFiles(Arrays.stream(listOfFiles).map(File::getName).collect(Collectors.toList()));
        }
        return project;
    }

    public List<ProjectDTO> findAll(){
        List<Project> projects = new ArrayList<>();
        this.projectRepository.findAll().forEach(projects::add);
        return projects.stream().map(ProjectDTO::new).collect(Collectors.toList());
    }

    public void save(Project project) {
        projectRepository.save(project);
    }

    public Project createProject(String name, String budget, String description, String category, String owner) throws InvalidProjectException, DataNotFoundException {
        User user = userService.findById(owner);
        Project project = user.createProject(name, BigDecimal.valueOf(Integer.parseInt(budget)), description, category);
        userService.save(user);
        // TODO : the mail sending causes a bug: user can keep pressing create on Front and create multiple projects!!!
        sendEmailService.sendSimpleMessage(user.getEmail(),
                "CREASTE UN PROYECTO",
                "El proyecto " + name + " ha sido creado con exito");
        return project;
    }

    public void deleteProject(Integer id) throws DataNotFoundException {
        // TODO: we should return budget to the owner money!!!
        Project project = findById(id);
        projectRepository.deleteById(id);
        fileService.deleteDirectoryAndFiles("src/main/resources/project/" + id + "/");
    }

    public void postFileToProject(MultipartFile file, Integer id) throws IOException {
        try {
            fileService.postFile(file, id.toString(), "project");
        } catch (IOException e) {
            throw new IOException("ERROR AL GUARDAR EL ARCHIVO " + file.getOriginalFilename());
        }
    }
}
