package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.DTO.UserDTO;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.service.UserService;
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

import java.util.List;

@Controller
@RequestMapping(path="/user")
@EnableAutoConfiguration
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public @ResponseBody
    ResponseEntity<?> getUser(@PathVariable("id") String id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok().body(user);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("User could not be found: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }

    //TODO: implement new user addition
    @RequestMapping(method = RequestMethod.POST, path = "/add")
    public @ResponseBody String addNewUser (@RequestParam String id, @RequestParam String email) {
        User n = new User();
        n.setNickname(id);
        n.setEmail(email);
        userService.save(n);
        return "Saved";
    }

    //TODO: implement user deletion
    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody String deleteUser(@RequestParam String id){
        userService.deleteUser(id);
        return "Deleted";
    }


}
