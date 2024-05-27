package org.example.conectatec.career.infrastructure;

import org.example.conectatec.career.domain.Career;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CareerRepository.class)
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
        Career savedCareer = careerRepository.save(career);
        Optional<Career> foundCareer = careerRepository.findById(savedCareer.getId());
        assertTrue(foundCareer.isPresent());
        assertEquals("Ingeniería Informática", foundCareer.get().getName());
    }

    @Test
    public void whenFindByName_thenCorrectCareer() {
        Career savedCareer = careerRepository.save(career);
        Optional<Career> foundCareer = careerRepository.findByName("Ingeniería Informática");
        assertTrue(foundCareer.isPresent());
        assertEquals(savedCareer.getId(), foundCareer.get().getId());
    }

    @Test
    public void whenInvalidName_thenCareerNotFound() {
        Optional<Career> foundCareer = careerRepository.findByName("No Existe");
        assertFalse(foundCareer.isPresent());
    }

    @Test
    public void whenDeleteById_thenCareerNotPresent() {
        Career savedCareer = careerRepository.save(career);
        careerRepository.deleteById(savedCareer.getId());
        Optional<Career> deletedCareer = careerRepository.findById(savedCareer.getId());
        assertFalse(deletedCareer.isPresent());
    }
}
