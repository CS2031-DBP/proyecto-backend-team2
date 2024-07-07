package org.example.conectatec.clubPost.infrastructure;

import org.example.conectatec.TestConectatecApplication;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.clubPost.domain.ClubFeed;
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
public class ClubPostRepositoryTest extends TestConectatecApplication {

    @Autowired
    private ClubPostRepository clubPostRepository;

    @Autowired
    private TestEntityManager entityManager;

    private ClubFeed clubPublication;
    private Career career;

    @BeforeEach
    public void setUp() {
        career = new Career();
        career.setFacultad("Facultad de Ciencias");
        career.setName("Ingeniería Informática");
        entityManager.persist(career);

        clubPublication = new ClubFeed();
        clubPublication.setCaption("Publicación de Prueba");
        clubPublication.setCareer(career);
        clubPublication.setComments(Collections.emptyList());
        entityManager.persist(clubPublication);
        entityManager.flush();
    }

    @Test
    public void cuandoBuscarPorId_entoncesRetornaPublicacionCorrecta() {
        Optional<ClubFeed> publicacionEncontrada = clubPostRepository.findById(clubPublication.getId());
        assertTrue(publicacionEncontrada.isPresent());
        assertEquals("Publicación de Prueba", publicacionEncontrada.get().getCaption());
    }

    @Test
    public void cuandoBuscarPorCarrera_entoncesRetornaPublicacionCorrecta() {
        Optional<ClubFeed> publicacionEncontrada = Optional.ofNullable(clubPostRepository.findByCareer(career));
        assertTrue(publicacionEncontrada.isPresent());
        assertEquals(clubPublication.getId(), publicacionEncontrada.get().getId());
    }

    @Test
    public void cuandoBuscarPorNombreInvalido_entoncesPublicacionNoEncontrada() {
        Optional<ClubFeed> publicacionEncontrada = clubPostRepository.findById(-1L);
        assertFalse(publicacionEncontrada.isPresent());
    }

    @Test
    public void cuandoEliminarPorId_entoncesPublicacionNoPresente() {
        clubPostRepository.deleteById(clubPublication.getId());
        Optional<ClubFeed> publicacionEliminada = clubPostRepository.findById(clubPublication.getId());
        assertFalse(publicacionEliminada.isPresent());
    }

    @Test
    public void cuandoGuardar_entoncesRetornaPublicacionCorrecta() {
        ClubFeed nuevaPublicacion = new ClubFeed();
        nuevaPublicacion.setCaption("Nueva Publicación");
        nuevaPublicacion.setCareer(career);
        nuevaPublicacion.setComments(Collections.emptyList());
        ClubFeed publicacionGuardada = clubPostRepository.save(nuevaPublicacion);
        assertNotNull(publicacionGuardada.getId());
        assertEquals("Nueva Publicación", publicacionGuardada.getCaption());
    }

    @Test
    public void cuandoGuardarYEliminar_entoncesPublicacionNoPresente() {
        ClubFeed nuevaPublicacion = new ClubFeed();
        nuevaPublicacion.setCaption("Otra Publicación");
        nuevaPublicacion.setCareer(career);
        nuevaPublicacion.setComments(Collections.emptyList());
        ClubFeed publicacionGuardada = clubPostRepository.save(nuevaPublicacion);
        assertNotNull(publicacionGuardada.getId());
        clubPostRepository.deleteById(publicacionGuardada.getId());
        Optional<ClubFeed> publicacionEliminada = clubPostRepository.findById(publicacionGuardada.getId());
        assertFalse(publicacionEliminada.isPresent());
    }
}
