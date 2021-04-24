package ar.edu.unq.tip.backendcooperar.model.DTO;

import ar.edu.unq.tip.backendcooperar.model.User;

public class UserDTO {

    private String nickname;

    private String email;

    public UserDTO(){

    }

    public UserDTO(User user){
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }
}
