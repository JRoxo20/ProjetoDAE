package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllEncomendas",
                query = "SELECT e FROM Encomenda e ORDER BY e.id" // JPQL
        )
})

@Table(
        name = "encomendas"
)
public class Encomenda {
    @Id
    private Long id;
    @OneToMany(mappedBy = "encomenda")
    private List<Volume> volumes;

    private String usernameCliente;
    @ManyToOne
    private Client client;




    public Long getId() {
        return id;
    }



    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsernameCliente() {
        return usernameCliente;
    }

    public void setUsernameCliente(String usernameCliente) {
        this.usernameCliente = usernameCliente;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    public Encomenda() {
    }

    public Encomenda(Long id, String usernameCliente, Client idCliente) {
        this.id = id;
        this.volumes = new ArrayList<>();
        this.usernameCliente = usernameCliente;
        this.client = client;

    }

    public void addVolume(Volume volume) {
        if(!volumes.contains(volume)){
            volumes.add(volume);
        }
    }

    public void removeVolume(Volume volume) {
        volumes.remove(volume);
    }

}
