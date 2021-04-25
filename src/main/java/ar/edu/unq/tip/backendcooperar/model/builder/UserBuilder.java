package ar.edu.unq.tip.backendcooperar.model.builder;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserBuilder {

    private String user_nickname = "default_nickname";
    private String user_password = "default_password";
    private String user_email = "default_email";
    private Set<Project> user_projects = new HashSet<>();

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public User build() {
        return new User(user_nickname, user_password, user_email, user_projects);
    }

    public UserBuilder withNickname(String nickname) {
        this.user_nickname = nickname;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.user_password = password;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.user_email = email;
        return this;
    }

    public UserBuilder withProjects(Set<Project> projects) {
        this.user_projects = projects;
        return this;
    }


}
