package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Encomenda;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;

import java.util.Date;
import java.util.List;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private EncomendaBean encomendaBean;

    @EJB
    private ProductBean produtoBean;


    public void create(Long id, String estado, String tipo_embalagem, Long idEncomenda) {
        var encomenda = encomendaBean.find(idEncomenda);
        var volume = new Volume(id, estado, tipo_embalagem, encomenda);
        entityManager.persist(volume);
    }


    public List<Volume> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllVolumes", Volume.class).getResultList();
    }

    public Volume find(Long id) {
        var volume = entityManager.find(Volume.class, id);
        if (volume == null) {
            throw new RuntimeException("volume " + id + " not found");
        }
        return volume;
    }

    public Volume mudarEstado(Long id)
    {
        var volume = entityManager.find(Volume.class, id);
        if (volume == null) {
            throw new RuntimeException("volume " + id + " not found");
        }
        if (volume.getEstado().compareTo("entregue") == 0)
        {
            throw new RuntimeException("volume " + id + " já foi entregue");
        }
        volume.setEstado("entregue");
        volume.setData_entrega(new Date());
        entityManager.persist(volume);
        return volume;
    }

    public Volume findWithSensores(Long id){
        var volume = this.find(id);
        Hibernate.initialize(volume.getSensors());
        return volume;
    }

    public Volume findWithProdutos(Long id){
        var volume = this.find(id);
        Hibernate.initialize(volume.getProdutos());
        return volume;
    }
}
