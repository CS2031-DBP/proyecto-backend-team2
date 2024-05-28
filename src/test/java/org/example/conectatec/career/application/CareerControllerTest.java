// CareerControllerTest.java
package org.example.conectatec.career.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.career.domain.CareerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CareerController.class)
@AutoConfigureMockMvc
public class CareerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CareerService careerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetCareerById() throws Exception {
        Career career = new Career();
        career.setId(1L);
        career.setName("Computer Science");

        Mockito.when(careerService.getCareerById(1L)).thenReturn(career);

        mockMvc.perform(get("/api/v1/career/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Computer Science"));
    }

    @Test
    public void testGetCareerById_NotFound() throws Exception {
        Mockito.when(careerService.getCareerById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/career/1"))
                .andExpect(status().isNotFound());
    }
}
