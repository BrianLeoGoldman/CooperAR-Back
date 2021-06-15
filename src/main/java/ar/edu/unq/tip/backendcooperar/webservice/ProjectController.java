package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.DTO.ProjectDTO;
import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidProjectException;
import ar.edu.unq.tip.backendcooperar.service.FileService;
import ar.edu.unq.tip.backendcooperar.service.ProjectService;
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
import org.springframework.web.bind.annotation.RestController;
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

@RestController
@RequestMapping(path="/project")
@EnableAutoConfiguration
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private FileService fileService;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public @ResponseBody
    ResponseEntity<?> getProject(@PathVariable("id") Integer id) {
        try {
            Project project = projectService.findById(id);
            String directory = "src/main/resources/project/" + id + "/";
            File folder = new File(directory);
            File[] listOfFiles = folder.listFiles();
            if(listOfFiles != null){
                project.setFiles(Arrays.stream(listOfFiles).map(File::getName).collect(Collectors.toList()));
            }
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
        } catch (InvalidProjectException e) {
            return new ResponseEntity<>("EL PROYECTO NO PUDO SER CREADO: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public @ResponseBody
    ResponseEntity<?> deleteProject(@PathVariable Integer id) {
        // TODO: delete files of the project!!!
        projectService.deleteProject(id);
        return ResponseEntity.ok().body("EL PROYECTO " + id + " HA SIDO ELIMINADO"); // TODO: not necessary to return
    }

    @RequestMapping(method = RequestMethod.POST, path = "/file/{id}")
    public @ResponseBody
    ResponseEntity<?> postFile(@RequestParam("file") MultipartFile file, @PathVariable Integer id) {
        try {
            fileService.postFile(file, id.toString(), "project");
        } catch (IOException e) {
            return new ResponseEntity<>("NO SE PUDO AGREGAR EL ARCHIVO AL PROYECTO: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

}
