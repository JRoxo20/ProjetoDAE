package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Dado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorEstado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorType;

import java.util.ArrayList;
import java.util.List;

public class SensorDTO {

    private long id;
    private SensorEstado estado;
    private SensorType tipo;

    private List<Dado> dados;


    public SensorDTO(long id, SensorEstado estado, SensorType tipo,List<Dado> dados) {
        this.id = id;
        this.estado = estado;
        this.tipo = tipo;
        this.dados = dados;
    }

    public SensorDTO() {
        this.dados = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SensorType getTipo() {
        return tipo;
    }

    public void setTipo(SensorType tipo) {
        this.tipo = tipo;
    }

    public List<Dado> getDados() {
        return dados;
    }

    public void setDados(List<Dado> dados) {
        this.dados = dados;
    }

    public SensorEstado getEstado() {
        return estado;
    }

    public void setEstado(SensorEstado estado) {
        this.estado = estado;
    }

    public static SensorDTO from(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getEstado(),
                sensor.getTipo(),
                sensor.getDados()
        );
    }
}
