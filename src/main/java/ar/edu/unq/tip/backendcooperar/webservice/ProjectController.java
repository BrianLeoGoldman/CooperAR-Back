package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.DTO.ProjectDTO;
import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidTaskException;
import ar.edu.unq.tip.backendcooperar.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path="/project")
@EnableAutoConfiguration
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public @ResponseBody
    ResponseEntity<?> getProject(@PathVariable("id") Integer id) {
        try {
            Project project = projectService.findById(id);
            return ResponseEntity.ok().body(project);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("Project could not be found: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getAllProjects() {
        List<ProjectDTO> list = projectService.findAll();
        return ResponseEntity.ok().body(list);
    }

    //TODO: implement new project addition
    @PostMapping
    public @ResponseBody
    String addNewProject (@RequestBody Project project) {
        projectService.addNewProject(project);
        return "Saved";
    }

    //TODO: implement project update
    @PutMapping
    public @ResponseBody
    String updateProject (@RequestBody Project project) {
        projectService.updateProject(project);
        return "Updated";
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public @ResponseBody
    ResponseEntity<?> deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().body("Ok");
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/addTask")
    public @ResponseBody
    ResponseEntity<?> createTask(@RequestParam String name,
                                 @RequestParam String reward,
                                 @RequestParam String description,
                                 @RequestParam String projectId,
                                 @RequestParam String difficulty,
                                 @RequestParam String owner) {
        try {
            Task task = projectService.createTask(name, reward, description, projectId, difficulty, owner);
            return ResponseEntity.ok().body(task);
        } catch (InvalidTaskException e) {
            return new ResponseEntity<>("NOOOOO: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
