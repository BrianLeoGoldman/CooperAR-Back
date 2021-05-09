package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

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
            return ResponseEntity.ok().body(task);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("Task could not be found: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getAllTasks() {
        List<Task> list = taskService.findAll();
        return ResponseEntity.ok().body(list);
    }

    //TODO: implement new task addition
    @PostMapping(path="/add")
    public @ResponseBody
    String addNewTask (@RequestParam String name, @RequestParam String description, @RequestParam int reward) {
        Task t = new Task();
        t.setName(name);
        t.setDescription(description);
        t.setReward(BigDecimal.valueOf(reward));
        taskService.save(t);
        return "Saved";
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public @ResponseBody
    ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().body("Ok");
    }


}
