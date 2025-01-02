package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;

import java.util.List;
import java.util.stream.Collectors;

public class VolumeDTO {
    @Id
    private Long id;
    private String estado;


    public VolumeDTO(Long id, String estado) {
        this.id = id;
        this.estado = estado;
    }

    public VolumeDTO() {
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


    // Converts an entity Volume to a DTO Volume class
    public static VolumeDTO from(Volume volume) {
        return new VolumeDTO(
                volume.getId(),
                volume.getEstado()
        );
    }
    // converts an entire list of entities into a list of DTOs
    public static List<VolumeDTO> from(List<Volume> courses) {
        return courses.stream().map(VolumeDTO::from).collect(Collectors.toList());
    }
}
