package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;


import jakarta.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllVolumes",
                query = "SELECT v FROM Volume v ORDER BY id" // JPQL
        )
})
@Table(name = "volumes")
public class Volume {
    @Id
    private Long id;
    private String estado;
    private String tipo_embalagem;
    //produtos
    //sensores


    public Volume(Long id, String estado, String tipo_embalagem) {
        this.id = id;
        this.estado = estado;
        this.tipo_embalagem = tipo_embalagem;
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

    public String getTipo_embalagem() {
        return tipo_embalagem;
    }

    public void setTipo_embalagem(String tipo_embalagem) {
        this.tipo_embalagem = tipo_embalagem;
    }
}
