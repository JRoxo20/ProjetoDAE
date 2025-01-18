package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.ProdutosNoVolume;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.VolumeEstado;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class VolumeDTO {
    @Id
    private Long id;
    private VolumeEstado estado;
    private String tipo_embalagem;
    private String data_entrega;
    private Long encomenda_id;
    private List<ProdutosNoVolumeDTO> produtos;
    private List<SensorDTO> sensores;


    public VolumeDTO(Long id, VolumeEstado estado, String tipo_embalagem, String data_entrega, Long encomenda_id, List<ProdutosNoVolumeDTO> produtos, List<SensorDTO> sensores) {
        this.id = id;
        this.estado = estado;
        this.tipo_embalagem = tipo_embalagem;
        this.data_entrega = data_entrega;
        this.encomenda_id = encomenda_id;
        this.produtos = produtos;
        this.sensores = sensores;
    }

    public VolumeDTO(Long id, VolumeEstado estado, String tipo_embalagem, String data_entrega, Long encomenda_id) {
        this.id = id;
        this.estado = estado;
        this.tipo_embalagem = tipo_embalagem;
        this.data_entrega = data_entrega;
        this.encomenda_id = encomenda_id;
        this.produtos = new ArrayList<>();
        this.sensores = new ArrayList<>();
    }

    public VolumeDTO() {
        this.produtos = new ArrayList<>();
        this.sensores = new ArrayList<>();
    }


    public List<SensorDTO> getSensores() {
        return sensores;
    }

    public void setSensores(List<SensorDTO> sensores) {
        this.sensores = sensores;
    }

    public List<ProdutosNoVolumeDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutosNoVolumeDTO> produtos) {
        this.produtos = produtos;
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

    public VolumeEstado getEstado() {
        return estado;
    }

    public void setEstado(VolumeEstado estado) {
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

    public static VolumeDTO fromComProdutos(Volume volume) {
        List<ProdutosNoVolumeDTO> list = new ArrayList<>();
        for (ProdutosNoVolume produtosNoVolume : volume.getProdutos()) {
            ProdutosNoVolumeDTO from = ProdutosNoVolumeDTO.from(produtosNoVolume);
            list.add(from);
        }
        List<SensorDTO> listSensores = new ArrayList<>();
        for (Sensor sensor : volume.getSensors()) {
            SensorDTO from = SensorDTO.from(sensor);
            listSensores.add(from);
        }
        return new VolumeDTO(
                volume.getId(),
                volume.getEstado(),
                volume.getTipo_embalagem(),
                volume.getData_entrega(),
                volume.getEncomenda().getId(),
                list,
                listSensores
        );
    }
    // converts an entire list of entities into a list of DTOs
    public static List<VolumeDTO> from(List<Volume> volumes) {
        return volumes.stream().map(VolumeDTO::from).collect(Collectors.toList());
    }
}