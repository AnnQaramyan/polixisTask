package com.test.repository;

import com.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUserTest() {
     List<User> myList = new ArrayList<User>();
        User user = new User("Anna", "Qaramyan", new Date());
        myList.add(user);
        myList.add(new User("Ani", "Stepanyan", new Date()));
        myList.add(new User("Aniu", "Stepanyan", new Date()));
        myList.add(new User("Anie", "Stepanyan", new Date()));
        myList.add(new User("Anij", "Stepanyan", new Date()));
        myList.add(new User("Anisddf", "Stepanyan", new Date()));
        myList.add(new User("Anicxc", "Stepanyan", new Date()));
        myList.add(new User("Anisf", "Stepanyan", new Date()));
        myList.add(new User("Anicxzf", "Stepanyan", new Date()));

        List<User> createdUser = userRepository.saveAll(myList);
        // User createdUser = userRepository.save(user);
        assertNotNull(createdUser);
    }
}
