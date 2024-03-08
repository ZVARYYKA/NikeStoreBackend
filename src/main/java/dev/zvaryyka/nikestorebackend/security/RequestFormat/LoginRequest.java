package dev.zvaryyka.nikestorebackend.security.RequestFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    public String email;

    public String password;

}
