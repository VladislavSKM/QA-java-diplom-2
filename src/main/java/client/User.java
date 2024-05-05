package client;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class User {

    private final String email;
    private final String password;
    private final String name;

    public static User createRandom() {
        final String email = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        final String passwprd = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, passwprd, name);
    }

}
