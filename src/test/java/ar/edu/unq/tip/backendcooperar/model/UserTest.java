package ar.edu.unq.tip.backendcooperar.model;

import ar.edu.unq.tip.backendcooperar.model.builder.ProjectBuilder;
import ar.edu.unq.tip.backendcooperar.model.builder.UserBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void testUserName() {
        String nickname = "Marcelo";
        User user = UserBuilder.aUser().withNickname(nickname).build();
        assertEquals(nickname, user.getNickname());
    }

    @Test
    public void testUserEmail() {
        String email = "tomy@gmail.com";
        User user = UserBuilder.aUser().withEmail(email).build();
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testUserProjects(){
        List<Project> projects = new ArrayList<>();
        projects.add(ProjectBuilder.aProject().build());
        projects.add(ProjectBuilder.aProject().build());
        User user = UserBuilder.aUser().withProjects(projects).build();
        assertEquals(2, user.getProjects().size());
    }

}