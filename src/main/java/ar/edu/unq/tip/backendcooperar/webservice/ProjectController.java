package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.persistence.ProjectRepository;
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
@RequestMapping(path="/project")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping(path="/add")
    public @ResponseBody
    String addNewProject (@RequestParam String name, @RequestParam int money) {
        Project p = new Project();
        p.setName(name);
        p.setBudget(BigDecimal.valueOf(money));
        projectRepository.save(p);
        return "Saved";
    }

    @GetMapping(path="/fetch")
    public @ResponseBody
    Optional<Project> getProject(@RequestParam Integer id) {
        return projectRepository.findById(id);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
