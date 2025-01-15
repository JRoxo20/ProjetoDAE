package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class EncomendaDTO implements Serializable {
    private int id;
    private String usernameCliente;

    public EncomendaDTO() {
    }

    public EncomendaDTO(int id, String usernameCliente) {
        this.id = id;
        this.usernameCliente = usernameCliente;
    }

    public static EncomendaDTO from(Encomenda encomenda) {
        return new EncomendaDTO(
                encomenda.getId(),
                encomenda.getClient().getUsername()
        );
    }

    public static List<EncomendaDTO> from(List<Encomenda> encomendas) {
        return encomendas.stream().map(EncomendaDTO::from).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsernameCliente() {
        return usernameCliente;
    }

    public void setUsernameCliente(String usernameCliente) {
        this.usernameCliente = usernameCliente;
    }
}
