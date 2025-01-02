package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;


import jakarta.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllVolumes",
                query = "SELECT v FROM Volume v" // JPQL
        )
})
@Table(name = "volumes")
public class Volume {
    @Id
    private Long id;
    private String estado;
    //produtos
    //sensores


    public Volume(Long id, String estado) {
        this.id = id;
        this.estado = estado;
    }

    public Volume() {
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
}
