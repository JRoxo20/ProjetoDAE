package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class VolumeDTO {
    @Id
    private Long id;
    private String estado;
    private String tipo_embalagem;
    private String data_entrega;
    private Long encomenda_id;


    public VolumeDTO(Long id, String estado, String tipo_embalagem, String data_entrega, Long encomenda_id) {
        this.id = id;
        this.estado = estado;
        this.tipo_embalagem = tipo_embalagem;
        this.data_entrega = data_entrega;
        this.encomenda_id = encomenda_id;
    }

    public VolumeDTO() {
    }


    public Long getEncomenda_id() {
        return encomenda_id;
    }

    public void setEncomenda_id(Long encomenda_id) {
        this.encomenda_id = encomenda_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo_embalagem() {
        return tipo_embalagem;
    }

    public void setTipo_embalagem(String tipo_embalagem) {
        this.tipo_embalagem = tipo_embalagem;
    }

    public String getData_entrega() {
        return data_entrega;
    }

    public void setData_entrega(String data_entrega) {
        this.data_entrega = data_entrega;
    }

    // Converts an entity Volume to a DTO Volume class
    public static VolumeDTO from(Volume volume) {
        return new VolumeDTO(
                volume.getId(),
                volume.getEstado(),
                volume.getTipo_embalagem(),
                volume.getData_entrega(),
                volume.getEncomenda().getId()
        );
    }
    // converts an entire list of entities into a list of DTOs
    public static List<VolumeDTO> from(List<Volume> courses) {
        return courses.stream().map(VolumeDTO::from).collect(Collectors.toList());
    }
}
