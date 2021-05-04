package ar.edu.unq.tip.backendcooperar.model.builder;

import ar.edu.unq.tip.backendcooperar.model.Project;
import ar.edu.unq.tip.backendcooperar.model.Province;
import ar.edu.unq.tip.backendcooperar.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UserBuilder {

    private String user_nickname = "default_nickname";
    private String user_firstname = "default_firstname";
    private String user_lastname = "default_lastname";
    private String user_password = "default_password";
    private String user_email = "default_email";
    private LocalDate user_birthday = LocalDate.now();
    private String user_province = Province.BUENOS_AIRES.toString();
    private BigDecimal user_money = BigDecimal.valueOf(1);
    private Set<Project> user_projects = new HashSet<>();

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public User build() {
        return new User(
            user_nickname,
            user_firstname,
            user_lastname,
            user_password,
            user_email,
            user_birthday,
            user_province,
            user_money,
            user_projects);
    }

    public UserBuilder withNickname(String nickname) {
        this.user_nickname = nickname;
        return this;
    }

    public UserBuilder withFirstname(String firstname) {
        this.user_firstname = firstname;
        return this;
    }

    public UserBuilder withLastname(String lastname) {
        this.user_lastname = lastname;
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

    public UserBuilder withBirthday(LocalDate birthday) {
        this.user_birthday = birthday;
        return this;
    }

    public UserBuilder withProvince(String province) {
        this.user_province = province;
        return this;
    }

    public UserBuilder withMoney(BigDecimal money) {
        this.user_money = money;
        return this;
    }

    public UserBuilder withProjects(Set<Project> projects) {
        this.user_projects = projects;
        return this;
    }


}
