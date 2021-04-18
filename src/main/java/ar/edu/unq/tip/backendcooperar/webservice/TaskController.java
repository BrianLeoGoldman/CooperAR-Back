package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping(path="/task")
@EnableAutoConfiguration
public class TaskController {

    @Autowired
    private TaskService taskService;

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

    @GetMapping(path="/fetch")
    @CrossOrigin(origins = "http://localhost:4200")
    public @ResponseBody
    Optional<Task> getTask(@RequestParam Integer id) {
        return taskService.findById(id);
    }

    @GetMapping(path="/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public @ResponseBody Iterable<Task> getAllTasks() {
        return taskService.findAll();
    }
}
