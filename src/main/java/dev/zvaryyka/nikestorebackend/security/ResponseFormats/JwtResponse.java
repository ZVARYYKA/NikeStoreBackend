package dev.zvaryyka.nikestorebackend.security.ResponseFormats;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;

    private String type = "Bearer";

    private Long id;

    private String email;

    private String name;

    private String surname;
    private List<String> roles;

    public JwtResponse(String accessToken,
                       Long id, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public JwtResponse(String token, Long id,
                       String email,
                       String name,
                       String surname,
                       List<String> roles) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.roles = roles;
    }
}
