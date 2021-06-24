package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.DTO.UserDTO;
import ar.edu.unq.tip.backendcooperar.model.MoneyRequest;
import ar.edu.unq.tip.backendcooperar.model.enums.RequestState;
import ar.edu.unq.tip.backendcooperar.persistence.MoneyRequestRepository;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.model.exceptions.LoginException;
import ar.edu.unq.tip.backendcooperar.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private MoneyRequestRepository moneyRequestRepository;
    @Autowired private FileService fileService;
    @Autowired private SendEmailService sendEmailService;

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

    public void save(User user) {
        userRepository.save(user);
    }

    public void registerUser(User user) throws DataNotFoundException {
        if (userRepository.existsById(user.getNickname())){
            throw new DataNotFoundException("EL USUARIO " + user.getNickname() + " YA EXISTE");
        }
        save(user);
    }

    public void deleteUser(String nickname) {
        // TODO: validate that projects and tasks can be deleted!!!
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

    public MoneyRequest findMoneyRequestById(String id) throws DataNotFoundException {
        if (moneyRequestRepository.existsById(Integer.valueOf(id))){
            return moneyRequestRepository.findById(Integer.valueOf(id)).get();
        }
        else {
            throw new DataNotFoundException("EL PEDIDO DE CARGA " + id + " NO EXISTE");
        }
    }

    public List<MoneyRequest> findAllMoneyRequests(String state) {
        List<MoneyRequest> moneyRequests = new ArrayList<>();
        this.moneyRequestRepository.findAllByState(state).forEach(moneyRequests::add);
        moneyRequests.forEach(MoneyRequest::loadFiles);
        return moneyRequests;
    }

    public void approveMoneyRequest(String id) throws DataNotFoundException {
        MoneyRequest request = findMoneyRequestById(id);
        User user = findById(request.getRequester());
        request.setState(RequestState.APROBADO.name());
        user.receiveMoney(request.getMoneyRequested());
        moneyRequestRepository.save(request);
        save(user);
        sendEmailService.sendSimpleMessage(user.getEmail(),
                "PEDIDO DE CARGA APROBADO",
                "Tu pedido de carga de fondos por $" + request.getMoneyRequested() + " fue aprobado");
    }

    public void rejectMoneyRequest(String id) throws DataNotFoundException {
        MoneyRequest request = findMoneyRequestById(id);;
        User user = findById(request.getRequester());
        request.setState(RequestState.RECHAZADO.name());
        moneyRequestRepository.save(request);
        sendEmailService.sendSimpleMessage(user.getEmail(),
                "PEDIDO DE CARGA RECHAZADO",
                "Tu pedido de carga de fondos por $" + request.getMoneyRequested() + " fue rechazado");
    }

    public Map<String, String> getFile(String id, String type, String fileName) throws DataNotFoundException {
        try {
            File file = fileService.getFile("src/main/resources/request/" + id + "/" + type + "/" + fileName);
            String encodeImage = fileService.encodeImage(file);
            Map<String, String> imageMap = new HashMap<>();
            imageMap.put("content", encodeImage);
            return imageMap;
        } catch (IOException e) {
            throw new DataNotFoundException("NO SE PUDO RECUPERAR EL ARCHIVO " + fileName);
        }

    }
}
