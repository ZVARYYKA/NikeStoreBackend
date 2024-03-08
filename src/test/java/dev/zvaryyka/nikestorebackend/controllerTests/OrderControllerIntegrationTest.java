package dev.zvaryyka.nikestorebackend.controllerTests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zvaryyka.nikestorebackend.dto.OrderDTO;
import dev.zvaryyka.nikestorebackend.dto.SneakersDTO;
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

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest {

    private final MockMvc mockMvc;
    private String accessToken;
    @Autowired
    public OrderControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    public void testCreateOrderByUser() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setAbout("About");
        orderDTO.setAddress("Pivo");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/createNewOrders")
                .header("Authorization", "Bearer " + accessToken)
                .content(asJsonString(orderDTO))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated());

    }
}
