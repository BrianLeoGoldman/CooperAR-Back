package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidTaskException;
import ar.edu.unq.tip.backendcooperar.persistence.ProjectRepository;
import ar.edu.unq.tip.backendcooperar.persistence.TaskRepository;
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

    public Task createTask(String name, String reward, String description, String projectId, String difficulty, String owner) throws InvalidTaskException {
        if(projectRepository.existsById(Integer.valueOf(projectId))){
            Project project = projectRepository.findById(Integer.valueOf(projectId)).get();
            Task task = project.createTask(name, description, BigDecimal.valueOf(Integer.parseInt(reward)), difficulty);
            projectRepository.save(project);
            return task;
        }
        else {
            throw new InvalidTaskException("LA TAREA NO SE PUDO CREAR PORQUE EL PROYECTO " + projectId + " NO EXISTE");
        }
    }

    public void deleteTask(Integer id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        }
    }

}
