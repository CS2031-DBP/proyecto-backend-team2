package org.example.conectatec.career.infrastructure;

import org.example.conectatec.career.domain.Career;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class CareerRepositoryTest {

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Career career;

    @BeforeEach
    public void setUp() {
        career = new Career();
        career.setFacultad("Facultad de Ciencias");
        career.setName("Ingeniería Informática");
        entityManager.persist(career);
        entityManager.flush();
    }

    @Test
    public void whenFindById_thenCorrectCareer() {
        Optional<Career> foundCareer = careerRepository.findById(career.getId());
        assertTrue(foundCareer.isPresent());
        assertEquals("Ingeniería Informática", foundCareer.get().getName());
    }

    @Test
    public void whenFindByName_thenCorrectCareer() {
        Optional<Career> foundCareer = careerRepository.findByName("Ingeniería Informática");
        assertTrue(foundCareer.isPresent());
        assertEquals(career.getId(), foundCareer.get().getId());
    }

    @Test
    public void whenInvalidName_thenCareerNotFound() {
        Optional<Career> foundCareer = careerRepository.findByName("No Existe");
        assertFalse(foundCareer.isPresent());
    }

    @Test
    public void whenDeleteById_thenCareerNotPresent() {
        careerRepository.deleteById(career.getId());
        Optional<Career> deletedCareer = careerRepository.findById(career.getId());
        assertFalse(deletedCareer.isPresent());

    }
}
