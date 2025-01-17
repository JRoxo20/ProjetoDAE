package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;

import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorEstado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorType;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSensors",
                query = "SELECT s FROM Sensor s ORDER BY s.id" // JPQL
        ),
        @NamedQuery(
                name = "getSensorByType",
                query = "SELECT s FROM Sensor s WHERE s.tipo = :tipo"
        ),
        @NamedQuery(
                name = "getSensorByStatus",
                query = "SELECT s FROM Sensor s WHERE s.estado = :estado"
        ),
        @NamedQuery(
                name = "getSensorByTypeAndEstado",
                query = "SELECT s FROM Sensor s WHERE s.tipo = :tipo AND s.estado = :estado"
        )
})
@Table(name = "sensores")
public class Sensor implements Serializable {
    @Id
    private Long id;

    @Column(nullable = false)
    private SensorEstado estado;

    private SensorType tipo;

    // Relacionamento OneToMany com a entidade "Dado"
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "sensor",fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Dado> dados;

    //relacionamento com volumes
    @ManyToOne
    private Volume volume;

    public Sensor() {
        this.dados = new ArrayList<>();
    }

    // Getters e Setters
    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SensorType getTipo() {
        return tipo;
    }

    public void setTipo(SensorType tipo) {
        this.tipo = tipo;
    }

    public SensorEstado getEstado() {
        return estado;
    }

    public void setEstado(SensorEstado estado) {
        this.estado = estado;
    }

    public List<Dado> getDados() {
        return dados;
    }

    public void setDados(List<Dado> dados) {
        this.dados = dados;
    }

    public void addDado(Dado dado) {
        dado.setSensor(this); // Associar o dado ao sensor
        this.dados.add(dado);
    }

    public void removeDado(Dado dado) {
        dado.setSensor(null);
        this.dados.remove(dado);
    }

    //constructor



    public Sensor(Long id ,SensorType tipo, Volume volume) {
        this.id = id;
        this.estado = SensorEstado.ATIVO;
        this.tipo = tipo;
        this.dados = new ArrayList<>();
        this.volume = volume;
    }




}
