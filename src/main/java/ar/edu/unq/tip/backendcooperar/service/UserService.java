package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.DTO.UserDTO;
import ar.edu.unq.tip.backendcooperar.model.Task;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.LoginException;
import ar.edu.unq.tip.backendcooperar.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void loginUser(String nickname, String password) throws LoginException {
        if(!userRepository.existsById(nickname)) {
            throw new LoginException("EL NOMBRE DE USUARIO NO ES CORRECTO");
        }
        if(!userRepository.loginUser(nickname, password)) {
            throw new LoginException("LA CONTRASEÑA NO ES CORRECTA");
        }
    }

    public User findById(String nickname) throws DataNotFoundException {
        if(userRepository.existsById(nickname)){
            return userRepository.findByNickname(nickname).get();
        }
        else {
            throw new DataNotFoundException("EL USUARIO " + nickname + " NO EXISTE");
        }
    }

    public List<UserDTO> findAll() {
        List<User> users = new ArrayList<>();
        this.userRepository.findAll().forEach(users::add);
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public void registerUser(User user) throws DataNotFoundException {
        if (userRepository.existsById(user.getNickname())){
            throw new DataNotFoundException("EL USUARIO " + user.getNickname() + " YA EXISTE");
        }
        userRepository.save(user);
    }

    public void deleteUser(String nickname) {
        if (userRepository.existsById(nickname)) {
            userRepository.deleteById(nickname);
        }
    }
}
