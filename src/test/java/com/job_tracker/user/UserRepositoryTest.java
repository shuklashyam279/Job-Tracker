package com.job_tracker.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail() {
        // Arrange
        User user = new User();
        user.setEmail("shyamshukla279@gmail.com");
        user.setFullName("Shyam Shukla");
        user.setPassword("12345678");
        userRepository.save(user);

        // Act
        User actualUser = userRepository.findByEmail("rahi@gmail.com").get();

        // Assert
        assertThat(actualUser).isEqualTo(user);
    }
}

