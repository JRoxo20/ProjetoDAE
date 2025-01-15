package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;

import jakarta.persistence.Entity;

import java.io.Serializable;
@Entity
public class Gestor extends User implements Serializable {

    private Boolean isGestor;

public Gestor() {
    }

    public Gestor(String username, String password, String name, String email, Boolean isGestor) {
        super(username, password, name, email);
        this.isGestor = true;

    }

    public Boolean getGestor() {
        return isGestor;
    }

    public void setGestor(Boolean gestor) {
        isGestor = gestor;
    }
}
