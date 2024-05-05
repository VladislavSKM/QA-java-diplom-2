package client;

import lombok.Data;

@Data
public class Credetntials {

    private final String email;
    private final String password;

    public static Credetntials fromUser(User user) {
        return new Credetntials(user.getEmail(), user.getPassword());
    }

    public static Credetntials fromUserWithoutPass(User user) {
        return new Credetntials(user.getEmail(), null);
    }

}
