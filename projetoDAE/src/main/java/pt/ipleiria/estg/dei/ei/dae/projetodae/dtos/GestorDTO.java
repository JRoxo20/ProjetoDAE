package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Gestor;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorDTO implements Serializable {

    private String username;
    private String name;
    private String email;
    private String password;
    private Boolean isGestor;



    public GestorDTO(String username, String name, String email, String password, Boolean isGestor) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isGestor = isGestor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static GestorDTO from(Gestor gestor) {
        return new GestorDTO(
                gestor.getUsername(),
                gestor.getName(),
                gestor.getEmail(),
                gestor.getPassword(),
                gestor.getGestor()
        );
    }

    public Boolean getGestor() {
        return isGestor;
    }

    public void setGestor(Boolean gestor) {
        isGestor = gestor;
    }

    public static List<GestorDTO> from(List<Gestor> gestores) {
        return gestores.stream().map(GestorDTO::from).collect(Collectors.toList());
    }
}
