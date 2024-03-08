package dev.zvaryyka.nikestorebackend.controllerTests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zvaryyka.nikestorebackend.models.Sneakers;
import dev.zvaryyka.nikestorebackend.security.RequestFormat.LoginRequest;
import dev.zvaryyka.nikestorebackend.security.RequestFormat.SignUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class SneakersControllerIntegrationTest {

    private final MockMvc mockMvc;
    private String accessToken;

    @Autowired
    public SneakersControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    public void authenticateUserAndGetToken() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("example@example.com12");
        loginRequest.setPassword("password123");

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .content(asJsonString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(response);
        accessToken = jsonResponse.get("token").asText();
    }

    @Test
    public void testGetAllSneakers() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/sneakers")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }
    @Test
    public void testOneSneakers() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/sneakers/13")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }
    @Test
    public void testAddSneakers() throws Exception {
        Sneakers newSneakers = new Sneakers();
        newSneakers.setName("New sneakers");
        newSneakers.setPrice(new BigDecimal("99"));
        newSneakers.setAbout("About");
        newSneakers.setImagePath("Image");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/sneakers")
                .header("Authorization","Bearer " + accessToken)
                .content(asJsonString(newSneakers))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated());

    }


    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
