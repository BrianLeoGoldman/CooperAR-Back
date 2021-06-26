package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.DTO.ProjectDTO;
import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.enums.TaskState;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidProjectException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidTaskException;
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
        sendEmailService.sendSimpleMessage(user.getEmail(),
                "CREASTE UN PROYECTO",
                "El proyecto " + name + " ha sido creado con exito");
        return project;
    }

    public void deleteProject(Integer id) throws DataNotFoundException, InvalidProjectException {
        Project project = findById(id);
        if (!project.isRemovable()) {
            throw new InvalidProjectException("EL PROYECTO TIENE TAREAS EN CURSO O ESPERANDO APROBACION");
        }
        User user = userService.findById(project.getOwner());
        BigDecimal recoveredRewards = project.rewardsToRecover();
        BigDecimal initialBudget = project.getBudget();
        project.receiveMoney(recoveredRewards);
        BigDecimal finalBudget = project.getBudget();
        user.receiveMoney(project.getBudget());
        // This next line has to be done before deleting project or it will re-save the project with new id
        userService.save(user);
        projectRepository.deleteById(id);
        fileService.deleteDirectoryAndFiles("src/main/resources/project/" + id + "/");
        sendEmailService.sendSimpleMessage(user.getEmail(),
                "EL PROYECTO " + project.getName() + " FUE ELIMINADO",
                "Se te han devuelto $" + finalBudget + " a tus fondos, en concepto de:" + "\n" +
                "$" + initialBudget + " como presupuesto del proyecto " + project.getName() + "\n" +
                "$" + recoveredRewards + " como recompensas de las tareas disponibles del proyecto");
    }

    public void postFileToProject(MultipartFile file, Integer id) throws IOException {
        try {
            fileService.postFile(file, id.toString(), "project");
        } catch (IOException e) {
            throw new IOException("ERROR AL GUARDAR EL ARCHIVO " + file.getOriginalFilename());
        }
    }
}
