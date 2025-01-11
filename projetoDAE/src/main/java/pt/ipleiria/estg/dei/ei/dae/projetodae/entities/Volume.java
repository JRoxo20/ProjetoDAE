package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;


import jakarta.persistence.*;

import java.util.Date;

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
    private Date data_entrega;
    //produtos
    //sensores


    public Volume(Long id, String tipo_embalagem) {
        this.id = id;
        this.estado = "em transito";
        this.tipo_embalagem = tipo_embalagem;
        this.data_entrega = null;
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

    public String getData_entrega() {
        if(data_entrega == null)
        {
            return null;
        }
        return data_entrega.toString();
    }

    public void setData_entrega(Date data_entrega) {
        this.data_entrega = data_entrega;
    }
}
