package ar.edu.unq.tip.backendcooperar.service;

import ar.edu.unq.tip.backendcooperar.model.DTO.UserDTO;
import ar.edu.unq.tip.backendcooperar.model.MoneyRequest;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.builder.UserBuilder;
import ar.edu.unq.tip.backendcooperar.model.enums.RequestState;
import ar.edu.unq.tip.backendcooperar.model.exceptions.DataNotFoundException;
import ar.edu.unq.tip.backendcooperar.persistence.MoneyRequestRepository;
import ar.edu.unq.tip.backendcooperar.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @InjectMocks private UserService userService;
    @Mock private UserRepository userRepository;
    @Mock private MoneyRequestRepository moneyRequestRepository;
    @Mock private SendEmailService sendEmailService;
    @Spy private FileService fileService;

    @Test
    public void testUserServiceFindAll() {
        MockitoAnnotations.openMocks(this);
        List<User> users = new ArrayList<>();
        String nickname_1 = "marcos789";
        String nickname_2 = "juana1988";
        users.add(UserBuilder.aUser().withNickname(nickname_1).build());
        users.add(UserBuilder.aUser().withNickname(nickname_2).build());
        when(userRepository.findAll()).thenReturn(users);
        List<UserDTO> recoveredUserDTOs = userService.findAll();
        assertEquals(2, recoveredUserDTOs.size());
        assertEquals(nickname_1, recoveredUserDTOs.get(0).getNickname());
        assertEquals(nickname_2, recoveredUserDTOs.get(1).getNickname());
    }

    @Test
    public void testUserServiceFindById() throws DataNotFoundException {
        MockitoAnnotations.openMocks(this);
        String nickname = "marcos789";
        User user = UserBuilder.aUser().withNickname(nickname).build();
        when(userRepository.existsById(nickname)).thenReturn(true);
        when(userRepository.findByNickname(nickname)).thenReturn(Optional.of(user));
        assertEquals(user, userService.findById(nickname));
    }

    @Test
    public void testUserServiceFindByIdForNonExistingUser() {
        MockitoAnnotations.openMocks(this);
        String nickname = "marcos789";
        when(userRepository.existsById(nickname)).thenReturn(false);
        try {
            userService.findById(nickname);
        } catch (DataNotFoundException e) {
            String message = "EL USUARIO " + nickname + " NO EXISTE";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testUserServiceRegisterUserForAlreadyExistingUser() {
        MockitoAnnotations.openMocks(this);
        String nickname = "marcos789";
        User user = UserBuilder.aUser().withNickname(nickname).build();
        when(userRepository.existsById(nickname)).thenReturn(true);
        try {
            userService.registerUser(user);
        } catch (DataNotFoundException e) {
            String message = "EL USUARIO " + user.getNickname() + " YA EXISTE";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testUserServiceSaveMoneyRequestFiles() throws IOException {
        MockitoAnnotations.openMocks(this);
        MoneyRequest request = new MoneyRequest("marcos789", BigDecimal.valueOf(Long.parseLong("2000")));
        request.setId(12);
        MockMultipartFile accountStatus
                = new MockMultipartFile(
                "accountStatus",
                "accountStatus.jpg",
                String.valueOf(MediaType.IMAGE_JPEG),
                "Image".getBytes()
        );
        MockMultipartFile depositReceipt
                = new MockMultipartFile(
                "depositReceipt",
                "depositReceipt.jpg",
                String.valueOf(MediaType.IMAGE_JPEG),
                "Image".getBytes()
        );
        userService.saveMoneyRequestFiles(accountStatus, depositReceipt, request);
        Mockito.doNothing().when(fileService).postFile(any(MultipartFile.class), any(), eq("request"));
        verify(fileService, times(2)).postFile(any(MultipartFile.class), any(), eq("request"));
    }

    @Test
    public void testUserFindAllMoneyRequests() throws DataNotFoundException {
        MockitoAnnotations.openMocks(this);
        String state = "ABIERTO";
        MoneyRequest request_1 = new MoneyRequest("marcos789", BigDecimal.valueOf(Long.parseLong("2000")));
        MoneyRequest request_2 = new MoneyRequest("maria_ana", BigDecimal.valueOf(Long.parseLong("5000")));
        List<MoneyRequest> moneyRequests = new ArrayList<>();
        moneyRequests.add(request_1);
        moneyRequests.add(request_2);
        when(moneyRequestRepository.findAllByState(state)).thenReturn(moneyRequests);
        assertEquals(moneyRequests, userService.findAllMoneyRequests(state));
    }

    @Test
    public void testUserApproveMoneyRequests() throws DataNotFoundException {
        MockitoAnnotations.openMocks(this);
        Integer id = 7;
        String nickname = "marcos789";
        BigDecimal initialMoney = BigDecimal.valueOf(3000);
        User user = UserBuilder.aUser().withMoney(initialMoney).build();
        BigDecimal requestedMoney = BigDecimal.valueOf(Long.parseLong("7000"));
        MoneyRequest request = new MoneyRequest(nickname, requestedMoney);
        when(moneyRequestRepository.existsById(id)).thenReturn(true);
        when(moneyRequestRepository.findById(id)).thenReturn(Optional.of(request));
        when(userRepository.existsById(nickname)).thenReturn(true);
        when(userRepository.findByNickname(nickname)).thenReturn(Optional.ofNullable(user));
        userService.approveMoneyRequest(id.toString());
        assertEquals(RequestState.APROBADO.name(), request.getState());
        assertEquals(initialMoney.add(requestedMoney), user.getMoney());
    }

    @Test
    public void testUserRejectMoneyRequests() throws DataNotFoundException {
        MockitoAnnotations.openMocks(this);
        Integer id = 7;
        String nickname = "marcos789";
        BigDecimal initialMoney = BigDecimal.valueOf(3000);
        User user = UserBuilder.aUser().withMoney(initialMoney).build();
        BigDecimal requestedMoney = BigDecimal.valueOf(Long.parseLong("7000"));
        MoneyRequest request = new MoneyRequest(nickname, requestedMoney);
        when(moneyRequestRepository.existsById(id)).thenReturn(true);
        when(moneyRequestRepository.findById(id)).thenReturn(Optional.of(request));
        when(userRepository.existsById(nickname)).thenReturn(true);
        when(userRepository.findByNickname(nickname)).thenReturn(Optional.ofNullable(user));
        userService.rejectMoneyRequest(id.toString());
        assertEquals(RequestState.RECHAZADO.name(), request.getState());
        assertEquals(initialMoney, user.getMoney());
    }

}