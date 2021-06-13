package com.test.repository;

import com.test.model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.batch.api.BatchProperty;
import java.util.ArrayList;
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
        User user = new User("Anna", "Qaramyan", 23456789);
        myList.add(user);
        myList.add(new User("Ani", "Stepanyan",234567));
        myList.add(new User("Varazdat", "Stepanyan", 5678));
        myList.add(new User("Anie", "Stepanyan", 22345678));
        myList.add(new User("Anij", "Stepanyan", 45678));
        myList.add(new User("Anisddf", "Stepanyan",78965));
        myList.add(new User("VArdenis", "Stepanyan", 23456789));
        myList.add(new User("Anisf", "Stepanyan", 123456789));
        myList.add(new User("Anicxzf", "Stepanyan",123456789));

        List<User> createdUser = userRepository.saveAll(myList);
        // User createdUser = userRepository.save(user);
        assertNotNull(createdUser);


    }
}
