package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO implements Serializable {

    private String username;
    private String name;
    private String email;
    private String password;
    private List<EncomendaDTO> encomendas;

    public ClientDTO() {
        this.encomendas = new ArrayList<>();
    }

    public ClientDTO(String username, String name, String email, String password, List<EncomendaDTO> encomendas) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.encomendas = encomendas;
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

    public static ClientDTO from(Client client) {
        return new ClientDTO(
                client.getUsername(),
                client.getName(),
                client.getEmail(),
                client.getPassword(),
                new ArrayList<EncomendaDTO>()
        );
    }

    public static List<ClientDTO> from(List<Client> clients) {
        return clients.stream().map(ClientDTO::from).collect(Collectors.toList());
    }
}
