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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SendEmailService sendEmailService;

    public Task findById(Integer id) throws DataNotFoundException {
        if(taskRepository.existsById(id)){
            return taskRepository.findById(id).get();
        }
        else {
            throw new DataNotFoundException("LA TAREA " + id + " NO EXISTE");
        }
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

    public Task createTask(String name, String reward, String description, String projectId, String difficulty, String owner) throws InvalidTaskException {
        if(projectRepository.existsById(Integer.valueOf(projectId))){
            Project project = projectRepository.findById(Integer.valueOf(projectId)).get();
            Task task = project.createTask(name, description, BigDecimal.valueOf(Integer.parseInt(reward)), difficulty);
            projectRepository.save(project);
            User user = userRepository.findByNickname(owner).get();
            sendEmailService.sendSimpleMessage(user.getEmail(),
                    "CREASTE UNA TAREA",
                    "La tarea " + name + " ha sido creada con exito");
            return task;
        }
        else {
            throw new InvalidTaskException("LA TAREA NO SE PUDO CREAR PORQUE EL PROYECTO " + projectId + " NO EXISTE");
        }
    }

    public void deleteTask(Integer id) {
        if (taskRepository.existsById(id)) {
            // TODO: we should return reward to the project budget!!!
            taskRepository.deleteById(id);
        }
    }

    public void assignWorker(String user, String id) throws InvalidTaskException {
        if(taskRepository.existsById(Integer.valueOf(id)) && userRepository.existsById(user)) {
            Task task = taskRepository.findById(Integer.valueOf(id)).get();
            task.setWorker(user);
            task.setState(TaskState.ASIGNADA.name());
            taskRepository.save(task);
            User taskOwner = userRepository.findByNickname(task.getOwner()).get();
            sendEmailService.sendSimpleMessage(taskOwner.getEmail(),
                    "TAREA ASIGNADA",
                    "El usuario " + user + " esta listo para trabajar en tu tarea " + task.getName());
        }
        else {
            throw new InvalidTaskException("EL USUARIO " + user + " O LA TAREA NO EXISTEN");
        }
    }

    public void unassignWorker(String id) throws InvalidTaskException {
        if(taskRepository.existsById(Integer.valueOf(id))) {
            Task task = taskRepository.findById(Integer.valueOf(id)).get();
            User oldWorker = userRepository.findByNickname(task.getWorker()).get();
            task.setWorker("DISPONIBLE");
            task.setState(TaskState.ABIERTA.name());
            taskRepository.save(task);
            sendEmailService.sendSimpleMessage(oldWorker.getEmail(),
                    "TAREA DESASIGNADA",
                    "Que lastima! Te han desasignado de la tarea " + task.getName());
        }
        else {
            throw new InvalidTaskException("LA TAREA " + id + " NO EXISTE");
        }
    }

    public void completeTask(String id) throws InvalidTaskException {
        if(taskRepository.existsById(Integer.valueOf(id))) {
            Task task = taskRepository.findById(Integer.valueOf(id)).get();
            User owner = userRepository.findByNickname(task.getOwner()).get();
            task.setState(TaskState.COMPLETA.name());
            taskRepository.save(task);
            sendEmailService.sendSimpleMessage(owner.getEmail(),
                    "TAREA COMPLETA",
                    "El usuario " + task.getWorker() + " ya ha terminado de trabajar en la tarea " + task.getName());
        }
        else {
            throw new InvalidTaskException("LA TAREA " + id + " NO EXISTE");
        }
    }

    public void approveTask(String id) throws InvalidTaskException {
        if(taskRepository.existsById(Integer.valueOf(id))) {
            Task task = taskRepository.findById(Integer.valueOf(id)).get();
            if(userRepository.existsById(task.getWorker())){
                User worker = userRepository.findByNickname(task.getWorker()).get();
                worker.receiveMoney(task.getReward()); // TODO: should we clean the reward of the task?
                task.setState(TaskState.CERRADA.name()); // TODO: should we clean the worker in the file?
                userRepository.save(worker);
                taskRepository.save(task);
                sendEmailService.sendSimpleMessage(worker.getEmail(),
                        "TAREA APROBADA",
                        "Felicidades! Han aprobado tu trabajo en la tarea " + task.getName() + " y has recibido $" + task.getReward());
            }
            else {
                throw new InvalidTaskException("EL USUARIO " + task.getWorker() + " NO EXISTE");
            }
        }
        else {
            throw new InvalidTaskException("LA TAREA " + id + " NO EXISTE");
        }
    }

    public void unapproveTask(String id) throws InvalidTaskException {
        if(taskRepository.existsById(Integer.valueOf(id))) {
            Task task = taskRepository.findById(Integer.valueOf(id)).get();
            task.setState(TaskState.ASIGNADA.name());
            taskRepository.save(task);
        }
        else {
            throw new InvalidTaskException("LA TAREA " + id + " NO EXISTE");
        }
    }

    public void cancelTask(String id) throws InvalidTaskException {
        if(taskRepository.existsById(Integer.valueOf(id))) {
            Task task = taskRepository.findById(Integer.valueOf(id)).get();
            task.setWorker("DISPONIBLE");
            task.setState(TaskState.CANCELADA.name());
            // TODO: we should return reward to the project budget!!!
            taskRepository.save(task);
        }
        else {
            throw new InvalidTaskException("LA TAREA " + id + " NO EXISTE");
        }
    }
}
