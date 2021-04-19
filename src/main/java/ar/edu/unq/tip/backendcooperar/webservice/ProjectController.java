package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping(path="/project")
@EnableAutoConfiguration
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    //@CrossOrigin(origins = "http://localhost:4200")
    public @ResponseBody
    String addNewProject (@RequestBody Project project) {
        projectService.addNewProject(project);
        return "Saved";
    }

    @PutMapping
    //@CrossOrigin(origins = "http://localhost:4200")
    public @ResponseBody
    String updateProject (@RequestBody Project project) {
        projectService.updateProject(project);
        return "Updated";
    }

    @DeleteMapping
    //@CrossOrigin(origins = "http://localhost:4200")
    public @ResponseBody
    void deleteProject(@RequestParam String id) {
        projectService.deleteProject(id);
    }

    @GetMapping(path="/fetch")
    //@CrossOrigin(origins = "http://localhost:4200")
    public @ResponseBody
    Optional<Project> getProject(@RequestParam String id) {
        return projectService.getProject(id);
    }

    @GetMapping(path="/all")
    //@CrossOrigin(origins = "http://localhost:4200")
    public @ResponseBody Iterable<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
}
