package dev.zvaryyka.nikestorebackend.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.zvaryyka.nikestorebackend.models.Person;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Getter
    private Long id;


    @Getter
    private String email;

    @JsonIgnore
    private String password;
    @Getter
    private String name;
    @Getter
    private String surname;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id,  String email, String password, String name,String surname,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Person user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getSurname(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}