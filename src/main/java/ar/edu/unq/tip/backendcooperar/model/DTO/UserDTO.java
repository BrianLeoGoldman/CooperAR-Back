package ar.edu.unq.tip.backendcooperar.model.DTO;

import ar.edu.unq.tip.backendcooperar.model.User;

public class UserDTO {

    private String nickname;

    private String firstname;

    private String lastname;

    private String email;

    public UserDTO(){

    }

    public UserDTO(User user){
        this.nickname = user.getNickname();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
    }

    public String getNickname() {
        return nickname;
    }

    public String getFirstname() { return firstname; }

    public String getLastname() { return lastname; }

    public String getEmail() {
        return email;
    }
}
