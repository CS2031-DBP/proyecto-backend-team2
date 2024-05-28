package org.example.conectatec.studentFeed.infrastructure;

import org.example.conectatec.TestConectatecApplication;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.studentFeed.domain.StudentFeed;
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
public class StudentFeedRepositoryTest extends TestConectatecApplication  {

    @Autowired
    private StudentFeedRepository studentFeedRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Career career;
    private Student student;
    private StudentFeed studentPublication;

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

        studentPublication = new StudentFeed();
        studentPublication.setHashtag("#IngenieriaInformatica");
        studentPublication.setMedia("media content");
        studentPublication.setStudent(student);
        entityManager.persist(studentPublication);

        entityManager.flush();
    }

    @Test
    public void cuandoBuscarPorId_entoncesRetornaPublicacionCorrecta() {
        StudentFeed publicacionEncontrada = studentFeedRepository.findById(studentPublication.getId()).orElse(null);
        assertNotNull(publicacionEncontrada);
        assertEquals("#IngenieriaInformatica", publicacionEncontrada.getHashtag());
    }

    @Test
    public void cuandoGuardar_entoncesRetornaPublicacionCorrecta() {
        StudentFeed nuevaPublicacion = new StudentFeed();
        nuevaPublicacion.setHashtag("#NuevaPublicacion");
        nuevaPublicacion.setMedia("nuevo media content");
        nuevaPublicacion.setStudent(student);

        StudentFeed publicacionGuardada = studentFeedRepository.save(nuevaPublicacion);
        assertNotNull(publicacionGuardada.getId());
        assertEquals("#NuevaPublicacion", publicacionGuardada.getHashtag());
    }

    @Test
    public void cuandoEliminarPorId_entoncesPublicacionNoPresente() {
        studentFeedRepository.deleteById(studentPublication.getId());
        StudentFeed publicacionEliminada = studentFeedRepository.findById(studentPublication.getId()).orElse(null);
        assertNull(publicacionEliminada);
    }

    @Test
    public void cuandoBuscarPorIdInvalido_entoncesPublicacionNoEncontrada() {
        StudentFeed publicacionEncontrada = studentFeedRepository.findById(-1L).orElse(null);
        assertNull(publicacionEncontrada);
    }

    @Test
    public void cuandoBuscarPorEstudiante_entoncesRetornaPublicacionesCorrectas() {
        List<StudentFeed> publicaciones = studentFeedRepository.findByStudent(student);
        assertFalse(publicaciones.isEmpty());
        assertEquals("#IngenieriaInformatica", publicaciones.get(0).getHashtag());
    }
}
