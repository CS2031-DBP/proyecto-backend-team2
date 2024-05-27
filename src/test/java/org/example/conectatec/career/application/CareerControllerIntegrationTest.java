package org.example.conectatec.career.application;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.career.infrastructure.CareerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class CareerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @BeforeEach
    public void setUp() {
        careerRepository.deleteAll();
    }

    @Test
    public void cuandoObtenerCareerPorId_entoncesRetornaCareer() throws Exception {
        Career career = new Career();
        career.setFacultad("Facultad de Ciencias");
        career.setName("Ingeniería Informática");
        career = careerRepository.save(career);

        mockMvc.perform(get("/career/{id}", career.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ingeniería Informática"))
                .andExpect(jsonPath("$.facultad").value("Facultad de Ciencias"));
    }

    @Test
    public void cuandoCrearCareer_entoncesRetornaCareerCreada() throws Exception {
        Career career = new Career();
        career.setFacultad("Facultad de Ciencias");
        career.setName("Ingeniería Informática");

        mockMvc.perform(post("/career")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(career)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ingeniería Informática"))
                .andExpect(jsonPath("$.facultad").value("Facultad de Ciencias"));
    }

    @Test
    public void cuandoEliminarCareerPorId_entoncesCareerEsEliminada() throws Exception {
        Career career = new Career();
        career.setFacultad("Facultad de Ciencias");
        career.setName("Ingeniería Informática");
        career = careerRepository.save(career);

        mockMvc.perform(delete("/career")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(career.getId())))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/career/{id}", career.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void cuandoObtenerTodasLasCareers_entoncesRetornaListaCareers() throws Exception {
        Career career1 = new Career();
        career1.setFacultad("Facultad de Ciencias");
        career1.setName("Ingeniería Informática");
        careerRepository.save(career1);

        Career career2 = new Career();
        career2.setFacultad("Facultad de Ciencias");
        career2.setName("Ingeniería Química");
        careerRepository.save(career2);

        mockMvc.perform(get("/career")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Ingeniería Informática"))
                .andExpect(jsonPath("$[1].name").value("Ingeniería Química"));
    }
}
