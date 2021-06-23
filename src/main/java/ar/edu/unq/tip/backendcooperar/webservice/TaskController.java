package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidTaskException;
import ar.edu.unq.tip.backendcooperar.service.FileService;
import ar.edu.unq.tip.backendcooperar.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path="/task")
@EnableAutoConfiguration
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public @ResponseBody
    ResponseEntity<?> getTask(@PathVariable("id") Integer id) {
        try {
            Task task = taskService.findById(id);
            return ResponseEntity.ok().body(task);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("ERROR AL BUSCAR LA TAREA: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getAllTasks() {
        List<Task> list = taskService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/assign")
    @ResponseBody
    public ResponseEntity<?> getAsignedTasks(@RequestParam String user) {
        List<Task> list = taskService.findAssignedTasks(user);
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> createTask(@RequestParam String name,
                                 @RequestParam String reward,
                                 @RequestParam String description,
                                 @RequestParam String projectId,
                                 @RequestParam String difficulty,
                                 @RequestParam String owner) {
        try {
            Task task = taskService.createTask(name, reward, description, projectId, difficulty, owner);
            return ResponseEntity.ok().body(task);
        } catch (InvalidTaskException e) {
            return new ResponseEntity<>("LA TAREA NO PUDO SER CREADA: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public @ResponseBody
    ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/file/{id}")
    public @ResponseBody
    ResponseEntity<?> postFile(@RequestParam("file") MultipartFile file, @PathVariable Integer id) {
        try {
            taskService.postFileToTask(file, id);
        } catch (IOException e) {
            return new ResponseEntity<>("NO SE PUDO AGREGAR EL ARCHIVO A LA TAREA: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/assign")
    public @ResponseBody
    ResponseEntity<?> assignWorker(@RequestParam String user, @RequestParam String id) {
        try {
            taskService.assignWorker(user, id);
            return ResponseEntity.ok().build();
        } catch (InvalidTaskException e) {
            return new ResponseEntity<>("NO SE PUDO ASIGNAR EL USUARIO A LA TAREA: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/unassign")
    public @ResponseBody
    ResponseEntity<?> unassignWorker(@RequestParam String id) {
        try {
            taskService.unassignWorker(id);
            return ResponseEntity.ok().build();
        } catch (InvalidTaskException e) {
            return new ResponseEntity<>("NO SE PUDO DESASIGNAR EL USUARIO A LA TAREA: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/complete")
    public @ResponseBody
    ResponseEntity<?> completeTask(@RequestParam String id) {
        try {
            taskService.completeTask(id);
            return ResponseEntity.ok().build();
        } catch (InvalidTaskException e) {
            return new ResponseEntity<>("NO SE PUDO COMPLETAR LA TAREA: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/approve")
    public @ResponseBody
    ResponseEntity<?> approveTask(@RequestParam String id) {
        try {
            taskService.approveTask(id);
            return ResponseEntity.ok().build();
        } catch (InvalidTaskException e) {
            return new ResponseEntity<>("NO SE PUDO APROBAR LA TAREA: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/unapprove")
    public @ResponseBody
    ResponseEntity<?> unapproveTask(@RequestParam String id) {
        try {
            taskService.unapproveTask(id);
            return ResponseEntity.ok().build();
        } catch (InvalidTaskException e) {
            return new ResponseEntity<>("NO SE PUDO DESAPROBAR LA TAREA: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/cancel")
    public @ResponseBody
    ResponseEntity<?> cancelTask(@RequestParam String id) {
        try {
            taskService.cancelTask(id);
            return ResponseEntity.ok().build();
        } catch (InvalidTaskException e) {
            return new ResponseEntity<>("NO SE PUDO CANCELAR LA TAREA: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
