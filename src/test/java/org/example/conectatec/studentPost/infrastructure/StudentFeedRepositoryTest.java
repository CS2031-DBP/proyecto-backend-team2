package org.example.conectatec.studentPost.infrastructure;

import org.example.conectatec.TestConectatecApplication;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.studentPost.domain.StudentPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class StudentPostRepositoryTest extends TestConectatecApplication  {

    @Autowired
    private StudentPostRepository studentPostRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Career career;
    private Student student;
    private StudentPost studentPublication;

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

        studentPublication = new StudentPost();
        studentPublication.setHashtag("#IngenieriaInformatica");
        studentPublication.setMedia("media content");
        studentPublication.setStudent(student);
        entityManager.persist(studentPublication);

        entityManager.flush();
    }

    @Test
    public void cuandoBuscarPorId_entoncesRetornaPublicacionCorrecta() {
        StudentPost publicacionEncontrada = studentPostRepository.findById(studentPublication.getId()).orElse(null);
        assertNotNull(publicacionEncontrada);
        assertEquals("#IngenieriaInformatica", publicacionEncontrada.getHashtag());
    }

    @Test
    public void cuandoGuardar_entoncesRetornaPublicacionCorrecta() {
        StudentPost nuevaPublicacion = new StudentPost();
        nuevaPublicacion.setHashtag("#NuevaPublicacion");
        nuevaPublicacion.setMedia("nuevo media content");
        nuevaPublicacion.setStudent(student);

        StudentPost publicacionGuardada = studentPostRepository.save(nuevaPublicacion);
        assertNotNull(publicacionGuardada.getId());
        assertEquals("#NuevaPublicacion", publicacionGuardada.getHashtag());
    }

    @Test
    public void cuandoEliminarPorId_entoncesPublicacionNoPresente() {
        studentPostRepository.deleteById(studentPublication.getId());
        StudentPost publicacionEliminada = studentPostRepository.findById(studentPublication.getId()).orElse(null);
        assertNull(publicacionEliminada);
    }

    @Test
    public void cuandoBuscarPorIdInvalido_entoncesPublicacionNoEncontrada() {
        StudentPost publicacionEncontrada = studentPostRepository.findById(-1L).orElse(null);
        assertNull(publicacionEncontrada);
    }

    @Test
    public void cuandoBuscarPorEstudiante_entoncesRetornaPublicacionesCorrectas() {
        List<StudentPost> publicaciones = studentPostRepository.findByStudent(student);
        assertFalse(publicaciones.isEmpty());
        assertEquals("#IngenieriaInformatica", publicaciones.get(0).getHashtag());
    }
}
