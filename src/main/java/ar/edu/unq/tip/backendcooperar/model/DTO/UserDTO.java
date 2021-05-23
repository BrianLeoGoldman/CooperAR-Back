package ar.edu.unq.tip.backendcooperar.model.DTO;

import ar.edu.unq.tip.backendcooperar.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UserDTO {

    private String nickname;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate birthday;
    private String province;
    private BigDecimal money;

    public UserDTO(){

    }

    public UserDTO(User user){
        this.nickname = user.getNickname();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.birthday = user.getBirthday();
        this.province = user.getProvince();
        this.money = user.getMoney();
    }

    public String getNickname() {
        return nickname;
    }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getEmail() {
        return email;
    }
    public LocalDate getBirthday() { return birthday; }
    public String getProvince() { return province; }
    public BigDecimal getMoney() { return money; }
}
