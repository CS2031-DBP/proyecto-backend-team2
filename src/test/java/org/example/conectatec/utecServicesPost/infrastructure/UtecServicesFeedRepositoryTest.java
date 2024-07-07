package org.example.conectatec.utecServicesPost.infrastructure;

import org.example.conectatec.TestConectatecApplication;
import org.example.conectatec.user.domain.Role;
import org.example.conectatec.utecServices.domain.UtecServices;
import org.example.conectatec.utecServicesPost.domain.UtecServicesPost;
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
    private UtecServicesPostRepository utecServicesPostRepository;

    @Autowired
    private TestEntityManager entityManager;

    private UtecServices utecService;
    private UtecServicesPost utecServicesFeed;

    @BeforeEach
    public void setUp() {
        utecService = new UtecServices();
        utecService.setName("Servicio de Biblioteca");
        utecService.setEmail("biblioteca@utec.edu.pe");
        utecService.setPassword("biblioteca123");
        utecService.setRole(Role.ADMIN);

         // Asignar un valor válido a role
        entityManager.persist(utecService);

        utecServicesFeed = new UtecServicesPost();
        utecServicesFeed.setHashtag("#Biblioteca");
        utecServicesFeed.setMedia("image.png");
        utecServicesFeed.setServicesUTEC(utecService);
        entityManager.persist(utecServicesFeed);

        entityManager.flush();
    }

    @Test
    public void cuandoBuscarPorId_entoncesRetornaFeedCorrecto() {
        Optional<UtecServicesPost> feedEncontrado = utecServicesPostRepository.findById(utecServicesFeed.getId());
        assertTrue(feedEncontrado.isPresent());
        assertEquals("#Biblioteca", feedEncontrado.get().getHashtag());
    }

    @Test
    public void cuandoBuscarPorHashtag_entoncesRetornaFeedCorrecto() {
        List<UtecServicesPost> feeds = utecServicesPostRepository.findByHashtag("#Biblioteca");
        assertFalse(feeds.isEmpty());
        assertEquals("#Biblioteca", feeds.get(0).getHashtag());
    }

    @Test
    public void cuandoBuscarPorServicesUTEC_entoncesRetornaFeedsCorrectos() {
        List<UtecServicesPost> feeds = utecServicesPostRepository.findByServicesUTEC(utecService);
        assertFalse(feeds.isEmpty());
        assertEquals(utecService.getId(), feeds.get(0).getServicesUTEC().getId());
    }

    @Test
    public void cuandoGuardar_entoncesRetornaFeedCorrecto() {
        UtecServicesPost nuevoPost = new UtecServicesPost();
        nuevoPost.setHashtag("#Laboratorio");
        nuevoPost.setMedia("lab.png");
        nuevoPost.setServicesUTEC(utecService);

        UtecServicesPost postGuardado = utecServicesPostRepository.save(nuevoPost);
        assertNotNull(postGuardado.getId());
        assertEquals("#Laboratorio", postGuardado.getHashtag());
    }

    @Test
    public void cuandoEliminarPorId_entoncesFeedNoPresente() {
        utecServicesPostRepository.deleteById(utecServicesPost.getId());
        Optional<UtecServicesPost> feedEliminado = utecServicesPostRepository.findById(utecServicesPost.getId());
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
