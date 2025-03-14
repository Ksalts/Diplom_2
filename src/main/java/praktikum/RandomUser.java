package praktikum;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomUser {
    public CreateUser random(){
        return new CreateUser(RandomStringUtils.randomAlphanumeric(10),RandomStringUtils.randomAlphanumeric(7), RandomStringUtils.randomAlphanumeric(7)+"@gmail.com");
    }
}
