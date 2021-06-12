package ar.edu.unq.tip.backendcooperar.webservice;

import ar.edu.unq.tip.backendcooperar.model.DTO.UserDTO;
import ar.edu.unq.tip.backendcooperar.model.User;
import ar.edu.unq.tip.backendcooperar.model.builder.UserBuilder;
import ar.edu.unq.tip.backendcooperar.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService service;

    @Test
    public void testGetAllUsersRequest() throws Exception {

        User user = UserBuilder.aUser().build();
        UserDTO userDTO = new UserDTO(user);
        List<UserDTO> allUsersDTO = Arrays.asList(userDTO);
        given(service.findAll()).willReturn(allUsersDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .header("Authorization", UserController.getJWTToken("default_user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nickname", is(userDTO.getNickname())))
                .andExpect(jsonPath("$[0].firstname", is(userDTO.getFirstname())));
    }

    @Test
    public void testGetSpecificUserRequest() throws Exception {

        String nickname = "juan123";
        User user = UserBuilder.aUser().withNickname(nickname).build();

        given(service.findById(nickname)).willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/" + nickname)
                .header("Authorization", UserController.getJWTToken("default_user"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.*", hasSize(9)))
                .andExpect(jsonPath("$.nickname", is(user.getNickname())))
                .andExpect(jsonPath("$.firstname", is(user.getFirstname())));
    }

}