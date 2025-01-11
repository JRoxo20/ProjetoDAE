package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;

import jakarta.persistence.*;

import java.util.LinkedList;

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
    private int idCliente;

    //private List<Volume> volumes;

    public int getId() {
        return id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    /*public List<Volume> getVolumes() {
        return volumes;
    }*/

    public void setId(int id) {
        this.id = id;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /*public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }*/

    public Encomenda() {
    }

    public Encomenda(int id, int idCliente) {
        this.id = id;
        this.idCliente = idCliente;
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
