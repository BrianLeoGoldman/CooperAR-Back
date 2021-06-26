package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.DTO.UserDTO;
import ar.edu.unq.tip.backendcooperar.model.MoneyRequest;
import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.InvalidProjectException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.LoginException;
import ar.edu.unq.tip.backendcooperar.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
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
            String token = getJWTToken(nickname);
            userService.loginUser(nickname, password);
            return ResponseEntity.ok().body(token);
        } catch (LoginException e) {
            return new ResponseEntity<>("ERROR AL LOGUEARSE: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public @ResponseBody
    ResponseEntity<?> getUser(@PathVariable("id") String id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok().body(user);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("ERROR AL BUSCAR EL USUARIO: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
            return new ResponseEntity<>("EL USUARIO NO PUDO SER CREADO: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{nickname}")
    public @ResponseBody
    ResponseEntity<?> deleteUser(@PathVariable String nickname){
        try {
            userService.deleteUser(nickname);
            return ResponseEntity.ok().build();
        } catch (DataNotFoundException | InvalidProjectException e) {
            return new ResponseEntity<>("EL USUARIO NO PUDO SER ELIMINADO: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/money")
    public @ResponseBody
    ResponseEntity<?> requestMoney(@RequestParam String user,
                                   @RequestParam String money,
                                   @RequestParam("accountStatus") MultipartFile accountStatus,
                                   @RequestParam("depositReceipt") MultipartFile depositReceipt){
        try {
            userService.requestMoney(user, money, accountStatus, depositReceipt);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return new ResponseEntity<>("EL PEDIDO DE CARGA DE DINERO NO SE PUDO CREAR: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/money")
    @ResponseBody
    public ResponseEntity<?> getMoneyRequests(@RequestParam String state) {
        List<MoneyRequest> list = userService.findAllMoneyRequests(state);
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/money/approve")
    public @ResponseBody
    ResponseEntity<?> approveMoneyRequest(@RequestParam String id){
        try {
            userService.approveMoneyRequest(id);
            return ResponseEntity.ok().build();
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("EL PEDIDO DE CARGA DE DINERO NO SE PUDO APROBAR: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/money/reject")
    public @ResponseBody
    ResponseEntity<?> rejectMoneyRequest(@RequestParam String id){
        try {
            userService.rejectMoneyRequest(id);
            return ResponseEntity.ok().build();
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("EL PEDIDO DE CARGA DE DINERO NO SE PUDO RECHAZAR: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/money/file")
    public @ResponseBody
    ResponseEntity<?> getFile(@RequestParam String id, @RequestParam String type, @RequestParam String fileName) {
        try {
            return ResponseEntity.ok().body(userService.getFile(id, type, fileName));
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("ERROR AL BUSCAR EL ARCHIVO: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    static String getJWTToken(String username) {
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
