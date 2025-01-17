package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class EncomendaDTO implements Serializable {
    @Id
    private Long id;
    private String usernameCliente;

    public EncomendaDTO() {
    }


    public EncomendaDTO(Long id, String usernameCliente) {
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

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getUsernameCliente() {
        return usernameCliente;
    }



    public void setUsernameCliente(String usernameCliente) {
        this.usernameCliente = usernameCliente;
    }
}
