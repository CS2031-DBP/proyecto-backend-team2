package org.example.conectatec.user.infrastructure;

import org.example.conectatec.TestConectatecApplication;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class UserBaseRepositoryTest extends TestConectatecApplication {

    @Autowired
    private UserBaseRepository userBaseRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Career career;
    private Student student;

    @BeforeEach
    public void setUp() {
        career = new Career();
        career.setFacultad("Facultad de Ciencias");
        career.setName("Ingeniería Informática");
        entityManager.persist(career);

        student = new Student();
        student.setName("Juan Pérez");
        student.setEmail("juan.perez@example.com");
        student.setPassword("password123");
        student.setCareer(career);
        entityManager.persist(student);

        entityManager.flush();
    }

    @Test
    public void cuandoBuscarPorId_entoncesRetornaUsuarioCorrecto() {
        Optional<User> usuarioEncontrado = userBaseRepository.findById(student.getId());
        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("Juan Pérez", usuarioEncontrado.get().getName());
    }

    @Test
    public void cuandoGuardar_entoncesRetornaUsuarioCorrecto() {
        Student nuevoEstudiante = new Student();
        nuevoEstudiante.setName("Ana María");
        nuevoEstudiante.setEmail("ana.maria@example.com");
        nuevoEstudiante.setPassword("password456");
        nuevoEstudiante.setCareer(career);

        User usuarioGuardado = (User) userBaseRepository.save(nuevoEstudiante);
        assertNotNull(usuarioGuardado.getId());
        assertEquals("Ana María", usuarioGuardado.getName());
    }

    @Test
    public void cuandoEliminarPorId_entoncesUsuarioNoPresente() {
        userBaseRepository.deleteById(student.getId());
        Optional<User> usuarioEliminado = userBaseRepository.findById(student.getId());
        assertFalse(usuarioEliminado.isPresent());
    }

    @Test
    public void cuandoBuscarPorIdInvalido_entoncesUsuarioNoEncontrado() {
        Optional<User> usuarioEncontrado = userBaseRepository.findById(-1L);
        assertFalse(usuarioEncontrado.isPresent());
    }

    @Test
    public void cuandoBuscarTodos_entoncesRetornaUsuariosCorrectos() {
        List<User> usuarios = userBaseRepository.findAll();
        assertFalse(usuarios.isEmpty());
        assertEquals("Juan Pérez", usuarios.get(0).getName());
    }
}
