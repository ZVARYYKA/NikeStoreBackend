package dev.zvaryyka.nikestorebackend.controllerTests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zvaryyka.nikestorebackend.security.RequestFormat.LoginRequest;
import dev.zvaryyka.nikestorebackend.security.RequestFormat.SignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {
    private final MockMvc mockMvc;

    private static String accessToken;

    @Autowired
    public AuthControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    //@Test
    public void testRegistration() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setName("Test");
        signUpRequest.setSurname("User");
        signUpRequest.setPassword("test123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                        .content(asJsonString(signUpRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testUserAuthentication() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("test123");

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .content(asJsonString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Извлекаем токен из JSON-ответа
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(response);
        String token = jsonResponse.get("token").asText();


        accessToken = token;
    }

    @Test
    public void testAdminAuthentication() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("example@example.com12");
        loginRequest.setPassword("password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .content(asJsonString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
