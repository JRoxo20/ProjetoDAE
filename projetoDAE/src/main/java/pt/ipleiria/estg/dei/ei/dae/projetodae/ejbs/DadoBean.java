package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Dado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorEstado;

@Stateless
public class DadoBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private SensorBean sensorBean;

    public void create(String valor,String mensagem, Long idSensor) {
        Sensor sensor = sensorBean.find(idSensor);
        if (sensor.getEstado() == SensorEstado.ATIVO) {
            Dado dado = new Dado(valor, mensagem, sensor);
            entityManager.persist(dado);
            sensor.addDado(dado);
            if (dado.getMensagem().compareTo("ERRO") == 0) {
                sensor.setEstado(SensorEstado.INATIVO);
            }
        }
        else
        {
            throw new RuntimeException("sensor " + idSensor + " Esta Inativo");
        }
    }

    public Dado find(Long id) {
        var dado = entityManager.find(Dado.class, id);
        if (dado == null) {
            throw new RuntimeException("dado " + id + " not found");
        }
        return dado;
    }
}
