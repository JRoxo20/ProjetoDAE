package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.ProdutosNoVolume;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Category;

@Stateless
public class ProdutosNoVolumeBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private VolumeBean volumeBean;
    @EJB
    private ProductBean productBean;

    public void create(Long produto_id, int quantidade, Long volume_id) {
        var volume = volumeBean.find(volume_id);
        if (volume == null)
        {
            return;
            //throw new MyEntityNotFoundException("volume \"" + volume_id + "\" not found");
        }
        var produto = productBean.findById(produto_id);
        if (produto == null)
        {
            return;
            //throw new MyEntityNotFoundException("volume \"" + volume_id + "\" not found");
        }
        try {
            ProdutosNoVolume produtosNoVolume = new ProdutosNoVolume(produto, quantidade, volume);
            entityManager.persist(produtosNoVolume);
            entityManager.flush();
            volume.addProduto(produtosNoVolume);
            produto.addProdutosNoVolume(produtosNoVolume);
        } catch (Exception e) {
            // throw exception
        }
    }


    public ProdutosNoVolume find(Long id) {
        var volume = entityManager.find(ProdutosNoVolume.class, id);
        if (volume == null) {
            throw new RuntimeException("produtos no volume " + id + " not found");
        }
        return volume;
    }


    public ProdutosNoVolume verifyId(Long id) {
        var volume = entityManager.find(ProdutosNoVolume.class, id);
        return volume;
    }
}
