package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Client extends User implements Serializable {

    @OneToMany(mappedBy = "client")
    private List<Encomenda> encomendas;

    public Client() {
        this.encomendas = new ArrayList<>();
    }

    public Client(String username, String password, String name, String email) {
        super(username, password, name, email);
        encomendas = new ArrayList<>();
    }

    public List<Encomenda> getEncomendas() {
        return encomendas;
    }

    public void setEncomendas(List<Encomenda> encomendas) {
        this.encomendas = encomendas;
    }

    public void addEncomenda(Encomenda encomenda) {
        encomendas.add(encomenda);
    }

    public void removeEncomenda(Encomenda encomenda) {
        encomendas.remove(encomenda);
    }

}
