package ar.edu.unq.tip.backendcooperar.model;

import ar.edu.unq.tip.backendcooperar.model.builder.ProjectBuilder;
import ar.edu.unq.tip.backendcooperar.model.builder.UserBuilder;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void testUserNickname() {
        String nickname = "Marcelo";
        User user = UserBuilder.aUser().withNickname(nickname).build();
        assertEquals(nickname, user.getNickname());
    }

    @Test
    public void testUserFirstname() {
        String firstname = "Tomas";
        User user = UserBuilder.aUser().withFirstname(firstname).build();
        assertEquals(firstname, user.getFirstname());
    }

    @Test
    public void testUserLastname() {
        String lastname = "Campos";
        User user = UserBuilder.aUser().withLastname(lastname).build();
        assertEquals(lastname, user.getLastname());
    }

    @Test
    public void testUserEmail() {
        String email = "tomy@gmail.com";
        User user = UserBuilder.aUser().withEmail(email).build();
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testUserProjects(){
        Set<Project> projects = new HashSet<>();
        projects.add(ProjectBuilder.aProject().build());
        projects.add(ProjectBuilder.aProject().build());
        User user = UserBuilder.aUser().withProjects(projects).build();
        assertEquals(2, user.getProjects().size());
    }

}