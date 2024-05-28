package org.example.conectatec.clubPublications.application;

import org.example.conectatec.club.domain.Club;
import org.example.conectatec.club.infrastructure.ClubRepository;
import org.example.conectatec.clubFeed.domain.ClubFeed;
import org.example.conectatec.clubFeed.infrastructure.ClubFeedRepository;
import org.example.conectatec.user.domain.Role;
import org.example.conectatec.user.domain.User;
import org.example.conectatec.user.infrastructure.UserBaseRepository;
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

@WithUserDetails(value = "testclub@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
@WithMockUser(roles = "CLUB")
@SpringBootTest
@AutoConfigureMockMvc
public class ClubPublicationsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClubFeedRepository clubPublicationsRepository;

    @Autowired
    private UserBaseRepository<User> userRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Reader reader;

    private String token = "";

    private void createUnauthorizedClub() throws Exception {
        String jsonContent = reader.readJsonFile("src/test/resources/club/post.json");
        jsonContent = reader.updateEmail(jsonContent, "email", "otherclub@example.com");

        mockMvc.perform(post("/student/register")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Limpiar los repositorios
        clubPublicationsRepository.deleteAll();
        clubRepository.deleteAll();
        userRepository.deleteAll();

        // Crear un club de prueba
        Club club = new Club();
        club.setName("Test Club");
        club.setEmail("testclub@example.com");
        club.setPassword(passwordEncoder.encode("password"));
        club.setRole(Role.CLUB);
        clubRepository.save(club);

        // Crear una publicación de prueba
        ClubFeed clubPublications = new ClubFeed();
        clubPublications.setCaption("Test Caption");
        clubPublications.setMedia("Test Media");
        clubPublications.setClub(club);
        clubPublicationsRepository.save(clubPublications);

        // Registrar y obtener el token
        String jsonContent = reader.readJsonFile("src/test/resources/auth/register.json");
        jsonContent = reader.updateEmail(jsonContent, "email", "testclub@example.com");

        var res = mockMvc.perform(post("/student/register")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        token = jsonObject.getString("token");
    }

    @Test
    public void testAuthorizedAccessToGetClubPublication() throws Exception {
        Long publicationId = clubPublicationsRepository.findAll().get(0).getId();

        mockMvc.perform(get("/club-publications/{id}", publicationId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    public void testStudentAccessToGetClubPublication() throws Exception {
        Long publicationId = clubPublicationsRepository.findAll().get(0).getId();

        mockMvc.perform(get("/club-publications/{id}", publicationId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "otherclub@example.com", roles = "CLUB")
    public void testUnauthorizedAccessToDeleteClubPublication() throws Exception {
        createUnauthorizedClub();
        Long authorizedPublicationId = clubPublicationsRepository.findAll().get(0).getId();

        mockMvc.perform(delete("/club-publications/{id}", authorizedPublicationId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAuthorizedAccessToUpdateClubPublication() throws Exception {
        Long authorizedPublicationId = clubPublicationsRepository.findAll().get(0).getId();
        String jsonContent = reader.readJsonFile("src/test/resources/clubPublications/patch.json");

        mockMvc.perform(patch("/club-publications/{id}", authorizedPublicationId)
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testUnauthorizedAccessToGetOwnClubPublication() throws Exception {
        Long authorizedPublicationId = clubPublicationsRepository.findAll().get(0).getId();

        mockMvc.perform(get("/club-publications/{id}", authorizedPublicationId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "otherclub@example.com", roles = "CLUB")
    public void testUnauthorizedAccessToUpdateClubPublication() throws Exception {
        createUnauthorizedClub();
        Long authorizedPublicationId = clubPublicationsRepository.findAll().get(0).getId();
        String jsonContent = reader.readJsonFile("src/test/resources/clubPublications/patch.json");

        mockMvc.perform(patch("/club-publications/{id}", authorizedPublicationId)
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isForbidden());
    }
}
