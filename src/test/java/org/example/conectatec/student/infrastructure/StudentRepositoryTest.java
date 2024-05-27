package org.example.conectatec.student.infrastructure;

import org.example.conectatec.TestConectatecApplication;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.student.domain.Student;
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
public class StudentRepositoryTest extends TestConectatecApplication  {

    @Autowired
    private StudentRepository studentRepository;

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
    public void cuandoBuscarPorId_entoncesRetornaEstudianteCorrecto() {
        Optional<Student> estudianteEncontrado = studentRepository.findById(student.getId());
        assertTrue(estudianteEncontrado.isPresent());
        assertEquals("Juan Pérez", estudianteEncontrado.get().getName());
    }

    @Test
    public void cuandoGuardar_entoncesRetornaEstudianteCorrecto() {
        Student nuevoEstudiante = new Student();
        nuevoEstudiante.setName("Ana García");
        nuevoEstudiante.setEmail("ana.garcia@example.com");
        nuevoEstudiante.setPassword("password456");
        nuevoEstudiante.setCareer(career);

        Student estudianteGuardado = studentRepository.save(nuevoEstudiante);
        assertNotNull(estudianteGuardado.getId());
        assertEquals("Ana García", estudianteGuardado.getName());
    }

    @Test
    public void cuandoEliminarPorId_entoncesEstudianteNoPresente() {
        studentRepository.deleteById(student.getId());
        Optional<Student> estudianteEliminado = studentRepository.findById(student.getId());
        assertFalse(estudianteEliminado.isPresent());
    }

    @Test
    public void cuandoBuscarPorIdInvalido_entoncesEstudianteNoEncontrado() {
        Optional<Student> estudianteEncontrado = studentRepository.findById(-1L);
        assertFalse(estudianteEncontrado.isPresent());
    }

    @Test
    public void cuandoBuscarPorCarrera_entoncesRetornaEstudiantesCorrectos() {
        List<Student> estudiantes = studentRepository.findByCareer(career);
        assertFalse(estudiantes.isEmpty());
        assertEquals("Juan Pérez", estudiantes.get(0).getName());
    }
}
