package org.example.conectatec.user.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.example.conectatec.user.domain.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserBaseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserBaseRepository<User> userBaseRepository;

    @BeforeEach
    void setUp() {
        // Aquí puedes agregar datos de prueba si es necesario antes de cada test
        User newUser = new User();
        newUser.setEmail("test@example.com");
        entityManager.persist(newUser);
        entityManager.flush();
    }

    @Test
    void findByEmailReturnsUser() {
        Optional<User> foundUser = userBaseRepository.findByEmail("test@example.com");
        assertTrue(foundUser.isPresent(), "User should be found with the email");
        assertEquals("test@example.com", foundUser.get().getEmail(), "Email should match");
    }

    @Test
    void findByEmailReturnsEmptyWhenUserNotPresent() {
        Optional<User> foundUser = userBaseRepository.findByEmail("notfound@example.com");
        assertFalse(foundUser.isPresent(), "User should not be found");
    }
}
