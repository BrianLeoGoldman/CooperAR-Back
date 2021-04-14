package ar.edu.unq.tip.backendcooperar.model.builder;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserBuilder {

    private String user_nickname = "default_nickname";
    private String user_email = "default_email";
    private List<Project> user_projects = new ArrayList<Project>();

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public User build() {
        User newUser = new User(user_nickname, user_email, user_projects);
        return newUser;
    }

    public UserBuilder withNickname(String nickname) {
        this.user_nickname = nickname;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.user_email = email;
        return this;
    }

    public UserBuilder withProjects(List<Project> projects) {
        this.user_projects = projects;
        return this;
    }

}
