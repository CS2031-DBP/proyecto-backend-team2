package org.example.conectatec.studentPublications.infrastructure;

import org.example.conectatec.TestConectatecApplication;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.studentPublications.domain.StudentPublications;
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
public class StudentPublicationsRepositoryTest extends TestConectatecApplication  {

    @Autowired
    private StudentPublicationsRepository studentPublicationsRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Career career;
    private Student student;
    private StudentPublications studentPublication;

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

        studentPublication = new StudentPublications();
        studentPublication.setHashtag("#IngenieriaInformatica");
        studentPublication.setMedia("media content");
        studentPublication.setStudent(student);
        entityManager.persist(studentPublication);

        entityManager.flush();
    }

    @Test
    public void cuandoBuscarPorId_entoncesRetornaPublicacionCorrecta() {
        StudentPublications publicacionEncontrada = studentPublicationsRepository.findById(studentPublication.getId()).orElse(null);
        assertNotNull(publicacionEncontrada);
        assertEquals("#IngenieriaInformatica", publicacionEncontrada.getHashtag());
    }

    @Test
    public void cuandoGuardar_entoncesRetornaPublicacionCorrecta() {
        StudentPublications nuevaPublicacion = new StudentPublications();
        nuevaPublicacion.setHashtag("#NuevaPublicacion");
        nuevaPublicacion.setMedia("nuevo media content");
        nuevaPublicacion.setStudent(student);

        StudentPublications publicacionGuardada = studentPublicationsRepository.save(nuevaPublicacion);
        assertNotNull(publicacionGuardada.getId());
        assertEquals("#NuevaPublicacion", publicacionGuardada.getHashtag());
    }

    @Test
    public void cuandoEliminarPorId_entoncesPublicacionNoPresente() {
        studentPublicationsRepository.deleteById(studentPublication.getId());
        StudentPublications publicacionEliminada = studentPublicationsRepository.findById(studentPublication.getId()).orElse(null);
        assertNull(publicacionEliminada);
    }

    @Test
    public void cuandoBuscarPorIdInvalido_entoncesPublicacionNoEncontrada() {
        StudentPublications publicacionEncontrada = studentPublicationsRepository.findById(-1L).orElse(null);
        assertNull(publicacionEncontrada);
    }

    @Test
    public void cuandoBuscarPorEstudiante_entoncesRetornaPublicacionesCorrectas() {
        List<StudentPublications> publicaciones = studentPublicationsRepository.findByStudent(student);
        assertFalse(publicaciones.isEmpty());
        assertEquals("#IngenieriaInformatica", publicaciones.get(0).getHashtag());
    }
}
