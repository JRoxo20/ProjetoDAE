package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.ProdutosNoVolume;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EncomendaDTO implements Serializable {
    @Id
    private Long id;
    private String usernameCliente;
    private List<VolumeDTO> volumes;

    public EncomendaDTO() {
        this.volumes = new ArrayList<>();
    }


    public EncomendaDTO(Long id, String usernameCliente) {
        this.id = id;
        this.usernameCliente = usernameCliente;
        this.volumes = new ArrayList<>();
    }

    public EncomendaDTO(Long id, String usernameCliente, List<VolumeDTO> volumes) {
        this.id = id;
        this.usernameCliente = usernameCliente;
        this.volumes = volumes;
    }

    public static EncomendaDTO from(Encomenda encomenda) {
        return new EncomendaDTO(
                encomenda.getId(),
                encomenda.getClient().getUsername()
        );
    }

    public static EncomendaDTO fromComVolumes(Encomenda encomenda) {
        List<VolumeDTO> list = new ArrayList<>();
        for (Volume volume : encomenda.getVolumes()) {
            VolumeDTO from = VolumeDTO.fromComProdutos(volume);
            list.add(from);
        }
        return new EncomendaDTO(
                encomenda.getId(),
                encomenda.getClient().getUsername(),
                list

        );
    }

    public static List<EncomendaDTO> from(List<Encomenda> encomendas) {
        return encomendas.stream().map(EncomendaDTO::from).collect(Collectors.toList());
    }


    public List<VolumeDTO> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<VolumeDTO> volumes) {
        this.volumes = volumes;
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
