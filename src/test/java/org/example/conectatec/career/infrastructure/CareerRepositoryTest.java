package org.example.conectatec.career.infrastructure;

import org.example.conectatec.TestConectatecApplication;
import org.example.conectatec.career.domain.Career;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CareerRepositoryTest extends TestConectatecApplication {

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
    public void cuandoBuscarPorId_entoncesRetornaCarreraCorrecta() {
        Optional<Career> carreraEncontrada = careerRepository.findById(career.getId());
        assertTrue(carreraEncontrada.isPresent());
        assertEquals("Ingeniería Informática", carreraEncontrada.get().getName());
    }

    @Test
    public void cuandoBuscarPorNombre_entoncesRetornaCarreraCorrecta() {
        Optional<Career> carreraEncontrada = careerRepository.findByName("Ingeniería Informática");
        assertTrue(carreraEncontrada.isPresent());
        assertEquals(career.getId(), carreraEncontrada.get().getId());
    }

    @Test
    public void cuandoBuscarPorNombreInvalido_entoncesCarreraNoEncontrada() {
        Optional<Career> carreraEncontrada = careerRepository.findByName("No Existe");
        assertFalse(carreraEncontrada.isPresent());
    }

    @Test
    public void cuandoEliminarPorId_entoncesCarreraNoPresente() {
        careerRepository.deleteById(career.getId());
        Optional<Career> carreraEliminada = careerRepository.findById(career.getId());
        assertFalse(carreraEliminada.isPresent());
    }

    @Test
    public void cuandoGuardar_entoncesRetornaCarreraCorrecta() {
        Career nuevaCarrera = new Career();
        nuevaCarrera.setFacultad("Facultad de Ingeniería");
        nuevaCarrera.setName("Ingeniería Civil");
        Career carreraGuardada = careerRepository.save(nuevaCarrera);
        assertNotNull(carreraGuardada.getId());
        assertEquals("Ingeniería Civil", carreraGuardada.getName());
    }

    @Test
    public void cuandoGuardarYEliminar_entoncesCarreraNoPresente() {
        Career nuevaCarrera = new Career();
        nuevaCarrera.setFacultad("Facultad de Ingeniería");
        nuevaCarrera.setName("Ingeniería Civil");
        Career carreraGuardada = careerRepository.save(nuevaCarrera);
        assertNotNull(carreraGuardada.getId());
        careerRepository.deleteById(carreraGuardada.getId());
        Optional<Career> carreraEliminada = careerRepository.findById(carreraGuardada.getId());
        assertFalse(carreraEliminada.isPresent());
    }
}
