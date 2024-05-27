package org.example.conectatec.utecServices.infrastructure;

import org.example.conectatec.TestConectatecApplication;
import org.example.conectatec.utecServices.domain.UtecServices;
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
public class UtecServicesRepositoryTest extends TestConectatecApplication {

    @Autowired
    private UtecServicesRepository utecServicesRepository;

    @Autowired
    private TestEntityManager entityManager;

    private UtecServices utecService;

    @BeforeEach
    public void setUp() {
        utecService = new UtecServices();
        utecService.setName("Servicio de Biblioteca");
        utecService.setEmail("biblioteca@utec.edu.pe");
        utecService.setPassword("biblioteca123");
        entityManager.persist(utecService);

        entityManager.flush();
    }

    @Test
    public void cuandoBuscarPorId_entoncesRetornaServicioCorrecto() {
        Optional<UtecServices> servicioEncontrado = utecServicesRepository.findById(utecService.getId());
        assertTrue(servicioEncontrado.isPresent());
        assertEquals("Servicio de Biblioteca", servicioEncontrado.get().getName());
    }

    @Test
    public void cuandoGuardar_entoncesRetornaServicioCorrecto() {
        UtecServices nuevoServicio = new UtecServices();
        nuevoServicio.setName("Servicio de Laboratorio");
        nuevoServicio.setEmail("laboratorio@utec.edu.pe");
        nuevoServicio.setPassword("laboratorio123");

        UtecServices servicioGuardado = utecServicesRepository.save(nuevoServicio);
        assertNotNull(servicioGuardado.getId());
        assertEquals("Servicio de Laboratorio", servicioGuardado.getName());
    }

    @Test
    public void cuandoEliminarPorId_entoncesServicioNoPresente() {
        utecServicesRepository.deleteById(utecService.getId());
        Optional<UtecServices> servicioEliminado = utecServicesRepository.findById(utecService.getId());
        assertFalse(servicioEliminado.isPresent());
    }

    @Test
    public void cuandoBuscarPorIdInvalido_entoncesServicioNoEncontrado() {
        Optional<UtecServices> servicioEncontrado = utecServicesRepository.findById(-1L);
        assertFalse(servicioEncontrado.isPresent());
    }

    @Test
    public void cuandoBuscarTodos_entoncesRetornaServiciosCorrectos() {
        List<UtecServices> servicios = utecServicesRepository.findAll();
        assertFalse(servicios.isEmpty());
        assertEquals("Servicio de Biblioteca", servicios.get(0).getName());
    }
}
