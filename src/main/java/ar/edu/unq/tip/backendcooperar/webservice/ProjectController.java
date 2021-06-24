package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.DTO.ProjectDTO;
import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidProjectException;
import ar.edu.unq.tip.backendcooperar.service.ProjectService;
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
            Project project = projectService.findProjectWithFiles(id);
            return ResponseEntity.ok().body(project);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("ERROR AL BUSCAR EL PROYECTO: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getAllProjects() {
        List<ProjectDTO> list = projectService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> createProject(@RequestParam String name,
                                    @RequestParam String budget,
                                    @RequestParam String description,
                                    @RequestParam String category,
                                    @RequestParam String owner) {
        try {
            Project project = projectService.createProject(name, budget, description, category, owner);
            return ResponseEntity.ok().body(project);
        } catch (InvalidProjectException | DataNotFoundException e) {
            return new ResponseEntity<>("EL PROYECTO NO PUDO SER CREADO: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public @ResponseBody
    ResponseEntity<?> deleteProject(@PathVariable Integer id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.ok().build();
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("EL PROYECTO NO PUDO SER ELIMINADO: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/file/{id}")
    public @ResponseBody
    ResponseEntity<?> postFile(@RequestParam("file") MultipartFile file, @PathVariable Integer id) {
        try {
            projectService.postFileToProject(file, id);
        } catch (IOException e) {
            return new ResponseEntity<>("NO SE PUDO AGREGAR EL ARCHIVO AL PROYECTO: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

}
