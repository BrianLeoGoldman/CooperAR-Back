package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.DTO.UserDTO;
import ar.edu.unq.tip.backendcooperar.model.MoneyRequest;
import ar.edu.unq.tip.backendcooperar.model.enums.RequestState;
import ar.edu.unq.tip.backendcooperar.persistence.MoneyRequestRepository;
import org.springframework.beans.factory.annotation.Value;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.LoginException;
import ar.edu.unq.tip.backendcooperar.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MoneyRequestRepository moneyRequestRepository;
    @Autowired
    private FileService fileService;

    public void loginUser(String nickname, String password) throws LoginException {
        if(nickname.equals("admin")) {
            if(!password.equals("admin12345")) {
                throw new LoginException("LA CONTRASEÑA DE ADMIN NO ES CORRECTA");
            }
        } else {
            if(!userRepository.existsById(nickname)) {
                throw new LoginException("EL NOMBRE DE USUARIO NO ES CORRECTO");
            }
            if(!userRepository.loginUser(nickname, password)) {
                throw new LoginException("LA CONTRASEÑA NO ES CORRECTA");
            }
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

    public void requestMoney(String user, String money, MultipartFile accountStatus, MultipartFile depositReceipt) throws IOException {
        MoneyRequest request = new MoneyRequest(user, BigDecimal.valueOf(Long.parseLong(money)));
        moneyRequestRepository.save(request);
        saveMoneyRequestFiles(accountStatus, depositReceipt, request);
    }

    protected void saveMoneyRequestFiles(MultipartFile accountStatus, MultipartFile depositReceipt, MoneyRequest request) throws IOException {
        try {
            fileService.postFile(accountStatus, request.getId().toString() + "/AS", "request");
            fileService.postFile(depositReceipt, request.getId().toString() + "/DR", "request");
        } catch (IOException e) {
            throw new IOException("SE FALLO AL INTENTAR GUARDAR EL ARCHIVO");
        }
    }

    public List<MoneyRequest> findAllMoneyRequests(String state) {
        List<MoneyRequest> moneyRequests = new ArrayList<>();
        // TODO: filter by state!!!
        this.moneyRequestRepository.findAllByState(state).forEach(moneyRequests::add);
        moneyRequests.forEach(MoneyRequest::loadFiles);
        return moneyRequests;
    }

    public void approveMoneyRequest(String id) throws DataNotFoundException {
        if (!moneyRequestRepository.existsById(Integer.valueOf(id))){
            throw new DataNotFoundException("EL PEDIDO DE CARGA " + id + " NO EXISTE");
        }
        MoneyRequest request = moneyRequestRepository.findById(Integer.valueOf(id)).get();
        request.setState(RequestState.APROBADO.name());
        if (!userRepository.existsById(request.getRequester())) {
            throw new DataNotFoundException("EL USUARIO " + request.getRequester() + " NO EXISTE");
        }
        User user = userRepository.findByNickname(request.getRequester()).get();
        user.receiveMoney(request.getMoneyRequested());
        userRepository.save(user);
        moneyRequestRepository.save(request);
        // TODO: send mail to user to inform request was approved!!!
    }

    public void rejectMoneyRequest(String id) throws DataNotFoundException {
        if (!moneyRequestRepository.existsById(Integer.valueOf(id))){
            throw new DataNotFoundException("EL PEDIDO DE CARGA " + id + " NO EXISTE");
        }
        MoneyRequest request = moneyRequestRepository.findById(Integer.valueOf(id)).get();
        request.setState(RequestState.RECHAZADO.name());
        if (!userRepository.existsById(request.getRequester())) {
            throw new DataNotFoundException("EL USUARIO " + request.getRequester() + " NO EXISTE");
        }
        User user = userRepository.findByNickname(request.getRequester()).get();
        moneyRequestRepository.save(request);
        // TODO: send mail to user to inform request was rejected!!!
    }

    public Map<String, String> getFile(String id, String type, String fileName) throws DataNotFoundException {
        try {
            PathResource path = new PathResource("src/main/resources/request/" + id + "/" + type + "/" + fileName);
            File file = path.getFile();
            String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath()));
            Map<String, String> jsonMap = new HashMap<>();
            jsonMap.put("content", encodeImage);
            return jsonMap;
        } catch (IOException e) {
            throw new DataNotFoundException("NO SE PUDO RECUPERAR EL ARCHIVO " + fileName);
        }

    }
}
