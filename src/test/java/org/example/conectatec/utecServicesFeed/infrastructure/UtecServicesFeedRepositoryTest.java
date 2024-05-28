package org.example.conectatec.utecServicesFeed.infrastructure;

import org.example.conectatec.TestConectatecApplication;
import org.example.conectatec.user.domain.Role;
import org.example.conectatec.utecServices.domain.UtecServices;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeed;
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
public class UtecServicesFeedRepositoryTest extends TestConectatecApplication {

    @Autowired
    private UtecServicesFeedRepository utecServicesFeedRepository;

    @Autowired
    private TestEntityManager entityManager;

    private UtecServices utecService;
    private UtecServicesFeed utecServicesFeed;

    @BeforeEach
    public void setUp() {
        utecService = new UtecServices();
        utecService.setName("Servicio de Biblioteca");
        utecService.setEmail("biblioteca@utec.edu.pe");
        utecService.setPassword("biblioteca123");
        utecService.setRole(Role.ADMIN);

         // Asignar un valor válido a role
        entityManager.persist(utecService);

        utecServicesFeed = new UtecServicesFeed();
        utecServicesFeed.setHashtag("#Biblioteca");
        utecServicesFeed.setMedia("image.png");
        utecServicesFeed.setServicesUTEC(utecService);
        entityManager.persist(utecServicesFeed);

        entityManager.flush();
    }

    @Test
    public void cuandoBuscarPorId_entoncesRetornaFeedCorrecto() {
        Optional<UtecServicesFeed> feedEncontrado = utecServicesFeedRepository.findById(utecServicesFeed.getId());
        assertTrue(feedEncontrado.isPresent());
        assertEquals("#Biblioteca", feedEncontrado.get().getHashtag());
    }

    @Test
    public void cuandoBuscarPorHashtag_entoncesRetornaFeedCorrecto() {
        List<UtecServicesFeed> feeds = utecServicesFeedRepository.findByHashtag("#Biblioteca");
        assertFalse(feeds.isEmpty());
        assertEquals("#Biblioteca", feeds.get(0).getHashtag());
    }

    @Test
    public void cuandoBuscarPorServicesUTEC_entoncesRetornaFeedsCorrectos() {
        List<UtecServicesFeed> feeds = utecServicesFeedRepository.findByServicesUTEC(utecService);
        assertFalse(feeds.isEmpty());
        assertEquals(utecService.getId(), feeds.get(0).getServicesUTEC().getId());
    }

    @Test
    public void cuandoGuardar_entoncesRetornaFeedCorrecto() {
        UtecServicesFeed nuevoFeed = new UtecServicesFeed();
        nuevoFeed.setHashtag("#Laboratorio");
        nuevoFeed.setMedia("lab.png");
        nuevoFeed.setServicesUTEC(utecService);

        UtecServicesFeed feedGuardado = utecServicesFeedRepository.save(nuevoFeed);
        assertNotNull(feedGuardado.getId());
        assertEquals("#Laboratorio", feedGuardado.getHashtag());
    }

    @Test
    public void cuandoEliminarPorId_entoncesFeedNoPresente() {
        utecServicesFeedRepository.deleteById(utecServicesFeed.getId());
        Optional<UtecServicesFeed> feedEliminado = utecServicesFeedRepository.findById(utecServicesFeed.getId());
        assertFalse(feedEliminado.isPresent());
    }

    @Test
    public void cuandoBuscarPorIdInvalido_entoncesFeedNoEncontrado() {
        Optional<UtecServicesFeed> feedEncontrado = utecServicesFeedRepository.findById(-1L);
        assertFalse(feedEncontrado.isPresent());
    }

    @Test
    public void cuandoBuscarTodos_entoncesRetornaFeedsCorrectos() {
        List<UtecServicesFeed> feeds = utecServicesFeedRepository.findAll();
        assertFalse(feeds.isEmpty());
        assertEquals("#Biblioteca", feeds.get(0).getHashtag());
    }
}
