package org.example.conectatec.career.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.career.domain.CareerService;
import org.example.conectatec.AppConfig.AppConfig;
import org.example.conectatec.career.infrastructure.CareerRepository;
import org.example.conectatec.utils.Reader;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(AppConfig.class) // Import the configuration
public class CareerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CareerService careerService;
    @Autowired
    private CareerRepository careerRepository;

    String token = "";

    @BeforeEach
    public void setUp() throws Exception {

        careerRepository.deleteAll();

        String jsonContent = Reader.readJsonFile("/career/post.json");

        var res = mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        token = jsonObject.getString("token");
        System.out.println("Token: " + token);

    }

    @Test
    public void testGetCareerByUser() throws Exception {
        Career career = new Career();
        career.setId(1L);
        career.setName("Computer Science");
        career.setFacultad("Computación");
        careerRepository.save(career);

        mockMvc.perform(get("/career/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Computer Science"))
                .andExpect(jsonPath("$.facultad").value("Computación"));
    }
}
