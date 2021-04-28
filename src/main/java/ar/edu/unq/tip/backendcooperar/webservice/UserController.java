package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.DTO.UserDTO;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.LoginException;
import ar.edu.unq.tip.backendcooperar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="/user")
@EnableAutoConfiguration
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, path = "/login")
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestParam("nickname") String nickname,
                                       @RequestParam("password") String password) {
        try {
            //System.out.println(nickname);
            //System.out.println(password);
            String token = getJWTToken(nickname);
            //User user = new User();
            //user.setUser(username);
            //user.setToken(token);
            //return user;
            userService.loginUser(nickname, password);
            return ResponseEntity.ok().body(token);
        } catch (LoginException e) {
            return new ResponseEntity<>("User login failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
    
    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<?> registerUser (@RequestBody User user) {
        try {
            String token = getJWTToken(user.getNickname());
            userService.registerUser(user);
            return new ResponseEntity<>(token, HttpStatus.CREATED);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("User could not be created: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TODO: implement user deletion
    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody String deleteUser(@RequestParam String id){
        userService.deleteUser(id);
        return "Deleted";
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        return "Bearer " + token;
    }


}
