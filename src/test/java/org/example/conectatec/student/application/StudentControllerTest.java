package org.example.conectatec.student.application;

import org.example.conectatec.student.infrastructure.StudentRepository;
import org.example.conectatec.utils.Reader;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Reader reader;

    private String token;

    @BeforeEach
    public void setUp() throws Exception {
        // Limpiar repositorios
        studentRepository.deleteAll();

        // Crear estudiante de prueba
        String jsonContent = reader.readJsonFile("src/test/resources/student/register_student.json");
        jsonContent = reader.updateJsonField(jsonContent, "email", "teststudent@example.com");

        var res = mockMvc.perform(post("/student/register")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        token = jsonObject.getString("token");
    }

    @Test
    @WithUserDetails(value = "teststudent@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void testGetStudentById() throws Exception {
        Long studentId = studentRepository.findAll().get(0).getId();

        mockMvc.perform(get("/student/{id}", studentId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testUnauthorizedAccessToGetStudentById() throws Exception {
        Long studentId = studentRepository.findAll().get(0).getId();

        mockMvc.perform(get("/student/{id}", studentId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAdminAccessToDeleteStudentById() throws Exception {
        Long studentId = studentRepository.findAll().get(0).getId();

        mockMvc.perform(delete("/student/{id}", studentId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}
