package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidTaskException;
import ar.edu.unq.tip.backendcooperar.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
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
            String directory = "src/main/resources/task/" + id + "/";
            File folder = new File(directory);
            File[] listOfFiles = folder.listFiles();
            if(listOfFiles != null){
                task.setFiles(Arrays.stream(listOfFiles).map(File::getName).collect(Collectors.toList()));
            }
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
        // TODO: delete files of the task!!!
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/file/{id}")
    public @ResponseBody
    ResponseEntity<?> postFile(@RequestParam("file") MultipartFile file, @PathVariable Integer id) throws IOException {
        // TODO: needs improvement!!!
        // Maximum size of the file: 1048576 bytes or 1048.576 kilobytes
        String directory = "src/main/resources/task/" + id + "/";
        Files.createDirectories(Paths.get(directory));
        File newFile = new File(directory + file.getOriginalFilename());
        try (OutputStream os = new FileOutputStream(newFile)) {
            os.write(file.getBytes());
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

}
