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
    private int id;

    private String usernameCliente;
    @ManyToOne
    private Client client;




    //private List<Volume> volumes;

    public int getId() {
        return id;
    }



    /*public List<Volume> getVolumes() {
        return volumes;
    }*/

    public void setId(int id) {
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

    /*public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }*/

    public Encomenda() {
    }

    public Encomenda(int id,String usernameCliente ,Client client) {
        this.id = id;
        this.usernameCliente = usernameCliente;
        this.client = client;

        //this.volumes = new List<>();
    }

    /*public void addVolume(Volume volume) {
        if(!volumes.contains(volume)){
            volumes.add(volume);
        }
    }

    public void removeVolume(Volume volume) {
        volumes.remove(volume);
     */

}
