package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class AuthDTO implements Serializable {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    // default constructor, getters and setters...

    public AuthDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

