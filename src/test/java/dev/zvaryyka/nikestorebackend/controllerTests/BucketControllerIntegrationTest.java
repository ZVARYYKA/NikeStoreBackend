package dev.zvaryyka.nikestorebackend.controllerTests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zvaryyka.nikestorebackend.dto.BucketDTO;
import dev.zvaryyka.nikestorebackend.dto.SneakersDTO;
import dev.zvaryyka.nikestorebackend.models.Sneakers;
import dev.zvaryyka.nikestorebackend.security.RequestFormat.LoginRequest;
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
public class BucketControllerIntegrationTest {

    private final MockMvc mockMvc;
    private String accessToken;

    @Autowired
    public BucketControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;

    }

    @BeforeEach
    public void authenticateUserAndGetToken() throws Exception {
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

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(response);
        accessToken = jsonResponse.get("token").asText();
    }

    @Test
    public void testAddSneakersInBucketByUser() throws Exception {
        SneakersDTO sneakersDTO = new SneakersDTO();
        sneakersDTO.setSneakersId(13);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bucket/addNew")
                .header("Authorization", "Bearer " + accessToken)
                .content(asJsonString(sneakersDTO))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void testDeleteSneakersFromBucketByUser() throws Exception {
        BucketDTO bucketDTO = new BucketDTO();
        bucketDTO.setBucketId(37);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/bucket/deleteThing")
                        .content(asJsonString(bucketDTO))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
