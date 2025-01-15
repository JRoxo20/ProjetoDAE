package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EncomendaDTO implements Serializable {
    private Long id;
    private int idCliente;
    private List<Volume> volumes;


    public EncomendaDTO() {
    }

    public EncomendaDTO(Long id, int idCliente) {
        this.id = id;
        this.idCliente = idCliente;
        volumes = new ArrayList<>();
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

    public Long getId() {
        return id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }



}
