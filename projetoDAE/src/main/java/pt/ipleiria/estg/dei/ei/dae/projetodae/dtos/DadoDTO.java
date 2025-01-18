package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Dado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Sensor;

import java.util.Date;

public class DadoDTO {

    @Id
    private Long id;

    private String valor;

    private Date timestamp;

    private String mensagem;
    private Sensor sensor;

    public DadoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public DadoDTO(Long id, String valor, Date timestamp, String mensagem, Sensor sensor) {
        this.id = id;
        this.valor = valor;
        this.timestamp = timestamp;
        this.mensagem = mensagem;
        this.sensor = sensor;
    }

    public static DadoDTO from (Dado dado){
        return new DadoDTO(
                dado.getId(),
                dado.getValor(),
                dado.getTimestamp(),
                dado.getMensagem(),
                dado.getSensor()
        );
    }
}
