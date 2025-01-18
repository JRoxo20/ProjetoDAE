package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Dado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Sensor;

@Stateless
public class DadoBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private SensorBean sensorBean;

    public void create(Long id, String valor,String mensagem, Long idSensor) {
        Sensor sensor = sensorBean.find(idSensor);
        Dado dado = new Dado(id, valor, mensagem, sensor);
        entityManager.persist(dado);
        sensor.addDado(dado);
    }

    public Dado find(Long id) {
        var dado = entityManager.find(Dado.class, id);
        if (dado == null) {
            throw new RuntimeException("dado " + id + " not found");
        }
        return dado;
    }
}
