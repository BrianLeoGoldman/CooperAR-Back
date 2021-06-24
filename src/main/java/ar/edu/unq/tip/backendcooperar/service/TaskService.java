package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.enums.TaskState;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidTaskException;
import ar.edu.unq.tip.backendcooperar.persistence.ProjectRepository;
import ar.edu.unq.tip.backendcooperar.persistence.TaskRepository;
import ar.edu.unq.tip.backendcooperar.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired private TaskRepository taskRepository;
    @Autowired private ProjectService projectService;
    @Autowired private UserService userService;
    @Autowired private FileService fileService;
    @Autowired private SendEmailService sendEmailService;

    public Task findById(Integer id) throws DataNotFoundException {
        if(taskRepository.existsById(id)) {
            return taskRepository.findById(id).get();
        }
        else {
            throw new DataNotFoundException("LA TAREA " + id + " NO EXISTE");
        }
    }

    public Task findTaskWithFiles(Integer id) throws DataNotFoundException {
        Task task = findById(id);
        File[] listOfFiles = fileService.getFilesFromDirectory("src/main/resources/task/" + id + "/");
        if(listOfFiles != null){
            task.setFiles(Arrays.stream(listOfFiles).map(File::getName).collect(Collectors.toList()));
        }
        return task;
    }

    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        this.taskRepository.findAll().forEach(tasks::add);
        return tasks;
    }

    public List<Task> findAssignedTasks(String nickname) {
        List<Task> tasks = new ArrayList<>();
        this.taskRepository.findAssignedTasks(nickname).forEach(tasks::add);
        return tasks;
    }

    public Task createTask(String name, String reward, String description, String projectId, String difficulty, String owner) throws InvalidTaskException, DataNotFoundException {
        Project project = projectService.findById(Integer.valueOf(projectId));
        Task task = project.createTask(name, description, BigDecimal.valueOf(Integer.parseInt(reward)), difficulty);
        projectService.save(project);
        User user = userService.findById(owner);
        updateProjectPercentage(Integer.valueOf(projectId));
        // TODO : the mail sending causes a bug: user can keep pressing create on Front and create multiple tasks!!!
        sendEmailService.sendSimpleMessage(user.getEmail(),
                "CREASTE UNA TAREA",
                "La tarea " + name + " ha sido creada con exito");
        return task;
    }

    public void deleteTask(Integer id) throws DataNotFoundException {
        // TODO: we should return reward to the project budget!!!
        Task task = findById(id);
        taskRepository.deleteById(id);
        updateProjectPercentage(task.getProjectId());
        fileService.deleteDirectoryAndFiles("src/main/resources/task/" + id + "/");
    }

    public void postFileToTask(MultipartFile file, Integer id) throws IOException {
        try {
            fileService.postFile(file, id.toString(), "task");
        } catch (IOException e) {
            throw new IOException("ERROR AL GUARDAR EL ARCHIVO " + file.getOriginalFilename());
        }
    }

    public void assignWorker(String user, String id) throws DataNotFoundException {
        Task task = findById(Integer.valueOf(id));
        task.setWorker(user);
        task.setState(TaskState.EN_CURSO.name());
        taskRepository.save(task);
        User taskOwner = userService.findById(task.getOwner());
        sendEmailService.sendSimpleMessage(taskOwner.getEmail(),
                "TAREA ASIGNADA",
                "El usuario " + user + " esta listo para trabajar en tu tarea " + task.getName());
    }

    public void unassignWorker(String id) throws InvalidTaskException, DataNotFoundException {
        Task task = findById(Integer.valueOf(id));
        User oldWorker = userService.findById(task.getWorker());
        task.setWorker("SIN TRABAJADOR");
        task.setState(TaskState.DISPONIBLE.name());
        taskRepository.save(task);
        sendEmailService.sendSimpleMessage(oldWorker.getEmail(),
                "TAREA DESASIGNADA",
                "Que lastima! Te han desasignado de la tarea " + task.getName());
    }

    public void completeTask(String id) throws DataNotFoundException {
        Task task = taskRepository.findById(Integer.valueOf(id)).get();
        User owner = userService.findById(task.getOwner());
        task.setState(TaskState.COMPLETA.name());
        taskRepository.save(task);
        sendEmailService.sendSimpleMessage(owner.getEmail(),
                "TAREA COMPLETA",
                "El usuario " + task.getWorker() + " ya ha terminado de trabajar en la tarea " + task.getName());
    }

    public void approveTask(String id) throws DataNotFoundException {
        Task task = findById(Integer.valueOf(id));
        User worker = userService.findById(task.getWorker());
        worker.receiveMoney(task.getReward()); // TODO: should we clean the reward of the task?
        task.setState(TaskState.FINALIZADA.name()); // TODO: should we clean the worker in the file?
        task.setFinishDate(LocalDate.now());
        userService.save(worker);
        taskRepository.save(task);
        updateProjectPercentage(task.getProjectId());
        sendEmailService.sendSimpleMessage(worker.getEmail(),
                "TAREA APROBADA",
                "Felicidades! Han aprobado tu trabajo en la tarea " + task.getName() + " y has recibido $" + task.getReward());
    }

    public void unapproveTask(String id) throws DataNotFoundException {
        Task task = findById(Integer.valueOf(id));
        task.setState(TaskState.EN_CURSO.name());
        taskRepository.save(task);
        User worker = userService.findById(task.getWorker());
        sendEmailService.sendSimpleMessage(worker.getEmail(),
                "TAREA DESAPROBADA",
                "Que mal! Han desaprobado tu trabajo en la tarea " + task.getName() + ", segui intentandolo...");
    }

    public void cancelTask(String id) throws DataNotFoundException {
        Task task = findById(Integer.valueOf(id));
        task.setWorker("SIN TRABAJADOR");
        task.setState(TaskState.CANCELADA.name());
        // TODO: we should return reward to the project budget!!!
        // TODO: update Project percentage!!!
        taskRepository.save(task);
    }

    private void updateProjectPercentage(Integer id) throws DataNotFoundException {
        Project project = projectService.findById(id); // TODO: check if project exists?
        project.calculatePercentage();
        projectService.save(project);
    }
}
