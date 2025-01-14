package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSensores",
                query = "SELECT s FROM Sensor s ORDER BY s.id" // JPQL
        )
})
@Table(name = "sensores")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática de IDs
    private Long id;

    @Column(nullable = false)
    private String estado;

    // Relacionamento OneToMany com a entidade "Dado"
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "sensor")
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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
    public Sensor(Long id, String estado) {
        this.id = id;
        this.estado = estado;
        this.dados = new ArrayList<>();
    }




}
