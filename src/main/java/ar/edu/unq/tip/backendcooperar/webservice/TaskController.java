package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.persistence.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping(path="/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping(path="/add")
    public @ResponseBody
    String addNewTask (@RequestParam String name, @RequestParam String description, @RequestParam int reward) {
        Task t = new Task();
        t.setName(name);
        t.setDescription(description);
        t.setReward(BigDecimal.valueOf(reward));
        taskRepository.save(t);
        return "Saved";
    }

    @GetMapping(path="/fetch")
    public @ResponseBody
    Optional<Task> getTask(@RequestParam Integer id) {
        return taskRepository.findById(id);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
