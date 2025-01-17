package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;

import jakarta.persistence.Entity;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Role;

import java.io.Serializable;

@Entity
public class Logistica extends User implements Serializable {

    public Logistica(String username, String name, String email, String password) {
        super(username, name, email, password, Role.LOGISTICA);
    }

    public Logistica() {
    }

}
