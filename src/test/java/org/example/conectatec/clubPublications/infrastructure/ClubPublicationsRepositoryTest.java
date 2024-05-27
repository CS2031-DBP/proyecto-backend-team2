package org.example.conectatec.clubPublications.infrastructure;

import org.example.conectatec.TestConectatecApplication;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.clubPublications.domain.ClubPublications;
import org.example.conectatec.commentBox.domain.CommentBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ClubPublicationsRepositoryTest extends TestConectatecApplication {

    @Autowired
    private ClubPublicationsRepository clubPublicationsRepository;

    @Autowired
    private TestEntityManager entityManager;

    private ClubPublications clubPublication;
    private Career career;

    @BeforeEach
    public void setUp() {
        career = new Career();
        career.setFacultad("Facultad de Ciencias");
        career.setName("Ingeniería Informática");
        entityManager.persist(career);

        clubPublication = new ClubPublications();
        clubPublication.setCaption("Publicación de Prueba");
        clubPublication.setCareer(career);
        clubPublication.setComments(Collections.emptyList());
        entityManager.persist(clubPublication);
        entityManager.flush();
    }

    @Test
    public void cuandoBuscarPorId_entoncesRetornaPublicacionCorrecta() {
        Optional<ClubPublications> publicacionEncontrada = clubPublicationsRepository.findById(clubPublication.getId());
        assertTrue(publicacionEncontrada.isPresent());
        assertEquals("Publicación de Prueba", publicacionEncontrada.get().getCaption());
    }

    @Test
    public void cuandoBuscarPorCarrera_entoncesRetornaPublicacionCorrecta() {
        Optional<ClubPublications> publicacionEncontrada = Optional.ofNullable(clubPublicationsRepository.findByCareer(career));
        assertTrue(publicacionEncontrada.isPresent());
        assertEquals(clubPublication.getId(), publicacionEncontrada.get().getId());
    }

    @Test
    public void cuandoBuscarPorNombreInvalido_entoncesPublicacionNoEncontrada() {
        Optional<ClubPublications> publicacionEncontrada = clubPublicationsRepository.findById(-1L);
        assertFalse(publicacionEncontrada.isPresent());
    }

    @Test
    public void cuandoEliminarPorId_entoncesPublicacionNoPresente() {
        clubPublicationsRepository.deleteById(clubPublication.getId());
        Optional<ClubPublications> publicacionEliminada = clubPublicationsRepository.findById(clubPublication.getId());
        assertFalse(publicacionEliminada.isPresent());
    }

    @Test
    public void cuandoGuardar_entoncesRetornaPublicacionCorrecta() {
        ClubPublications nuevaPublicacion = new ClubPublications();
        nuevaPublicacion.setCaption("Nueva Publicación");
        nuevaPublicacion.setCareer(career);
        nuevaPublicacion.setComments(Collections.emptyList());
        ClubPublications publicacionGuardada = clubPublicationsRepository.save(nuevaPublicacion);
        assertNotNull(publicacionGuardada.getId());
        assertEquals("Nueva Publicación", publicacionGuardada.getCaption());
    }

    @Test
    public void cuandoGuardarYEliminar_entoncesPublicacionNoPresente() {
        ClubPublications nuevaPublicacion = new ClubPublications();
        nuevaPublicacion.setCaption("Otra Publicación");
        nuevaPublicacion.setCareer(career);
        nuevaPublicacion.setComments(Collections.emptyList());
        ClubPublications publicacionGuardada = clubPublicationsRepository.save(nuevaPublicacion);
        assertNotNull(publicacionGuardada.getId());
        clubPublicationsRepository.deleteById(publicacionGuardada.getId());
        Optional<ClubPublications> publicacionEliminada = clubPublicationsRepository.findById(publicacionGuardada.getId());
        assertFalse(publicacionEliminada.isPresent());
    }
}
