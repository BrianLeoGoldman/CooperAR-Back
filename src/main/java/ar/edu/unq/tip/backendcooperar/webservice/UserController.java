package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping(path="/user")
@EnableAutoConfiguration
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String id, @RequestParam String email) {
        User n = new User();
        n.setNickname(id);
        n.setEmail(email);
        userService.save(n);
        return "Saved";
    }

    @DeleteMapping
    //@CrossOrigin(origins = "http://localhost:4200")
    public @ResponseBody String deleteUser(@RequestParam String id){
        userService.deleteUser(id);
        return "Deleted";
    }

    @GetMapping(path="/fetch")
    //@CrossOrigin(origins = "http://localhost:4200")
    public @ResponseBody
    Optional<User> getUser(@RequestParam String id) {
        return userService.findById(id);
    }

    @GetMapping(path="/all")
    //@CrossOrigin(origins = "http://localhost:4200")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userService.findAll();
    }
}
