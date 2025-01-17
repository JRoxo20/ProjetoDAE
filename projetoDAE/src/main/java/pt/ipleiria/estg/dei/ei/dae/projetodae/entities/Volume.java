package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.VolumeEstado;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllVolumes",
                query = "SELECT v FROM Volume v ORDER BY id" // JPQL
        ),
        @NamedQuery(
                name = "getAllVolumesByClient",
                query = "SELECT v FROM Volume v JOIN Encomenda e ON v.encomenda.id = e.id " +
                        "WHERE e.client.username = :usernameCliente ORDER BY v.id" // JPQL
        )
})
@Table(name = "volumes")
public class Volume {
    @Id
    private Long id;
    private VolumeEstado estado;
    private String tipo_embalagem;
    @ManyToOne
    @NotNull
    private Encomenda encomenda;
    private Date data_entrega;
    //produtos
    //@ManyToMany(mappedBy = "volumes")
    //private List<Product> products ;
    //sensores
    @OneToMany(mappedBy = "volume")
    private List<Sensor> sensors;
    @OneToMany(mappedBy = "volume")
    private List<Product> produtos;


    public Volume(Long id, String tipo_embalagem, Encomenda encomenda) {
        this.id = id;
        this.estado = VolumeEstado.Em_Transito;
        this.tipo_embalagem = tipo_embalagem;
        this.encomenda = encomenda;
        this.data_entrega = null;
        this.sensors = new ArrayList<>();
        this.produtos = new ArrayList<>();
    }

    public Volume() {
        //this.products = new ArrayList<>();
        this.sensors = new ArrayList<>();
        this.produtos = new ArrayList<>();
    }


    public List<Product> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Product> produtos) {
        this.produtos = produtos;
    }

    public void addProduto(Product product)
    {
        if (!produtos.contains(product))
        {
            produtos.add(product);
        }
    }

    public void removeProduto(Product product)
    {
        produtos.remove(product);
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public void addSensor(Sensor sensor)
    {
        if (!sensors.contains(sensor))
        {
            sensors.add(sensor);
        }
    }

    public void removeSensor(Sensor sensor)
    {
        sensors.remove(sensor);
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
        if(data_entrega == null)
        {
            return null;
        }
        return data_entrega.toString();
    }

    public void setData_entrega(Date data_entrega) {
        this.data_entrega = data_entrega;
    }


    public Encomenda getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }

}