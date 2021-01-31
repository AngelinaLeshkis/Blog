package com.example.blog.unit;

import com.example.blog.entity.User;
import com.example.blog.persistence.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepo;

    @Test
    public void saveUser() {
        User user = new User();

        user.setEmail("some@mail.ru");
        user.setPassword("password");
        user.setFirstName("firstname");
        user.setLastName("lastname");

        User savedUser = userRepo.findUserByEmail("leshkisangelina1@gmail.com")
                .orElse(  null);

        Assert.assertNotNull(savedUser);
        Assert.assertEquals(savedUser.getId(), Long.valueOf("8"));
    }
}

