package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "dados")
public class Dado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String valor;

    private Date timestamp;

    private String mensagem;

    // Relacionamento ManyToOne com a entidade "Sensor"
    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    @JsonBackReference
    private Sensor sensor;

    public Dado() {
    }

    // Getters e Setters
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

    public Date  getTimestamp() {
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

    public Dado(String valor, String mensagem, Sensor sensor) {
        this.id = id;
        this.valor = valor;
        this.timestamp = new Date();
        this.mensagem = mensagem;
        this.sensor = sensor;
    }
}
