package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class EncomendaDTO implements Serializable {
    private int id;
    private int idCliente;

    public EncomendaDTO() {
    }

    public EncomendaDTO(int id, int idCliente) {
        this.id = id;
        this.idCliente = idCliente;
    }

    public static EncomendaDTO from(Encomenda encomenda) {
        return new EncomendaDTO(
                encomenda.getId(),
                encomenda.getIdCliente()
        );
    }

    public static List<EncomendaDTO> from(List<Encomenda> encomendas) {
        return encomendas.stream().map(EncomendaDTO::from).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }




}
