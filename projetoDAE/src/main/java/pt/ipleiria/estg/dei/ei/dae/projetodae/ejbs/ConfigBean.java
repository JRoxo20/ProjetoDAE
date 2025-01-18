package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Role;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorEstado;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorType;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Category;
import pt.ipleiria.estg.dei.ei.dae.projetodae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projetodae.exceptions.MyEntityNotFoundException;

import java.util.List;


@Startup
@Singleton
public class ConfigBean {
    @EJB
    private UserBean userBean;
    @EJB
    private ClientBean clientBean;
    @EJB
    private GestorBean gestorBean;
    @EJB
    private LogisticaBean logisticaBean;
    @EJB
    private EncomendaBean encomendaBean;

    @EJB
    private SensorBean sensorBean;

    @EJB
    private DadoBean dadoBean;
    @EJB
    private VolumeBean volumeBean;

    @EJB
    private ProductBean productBean;
    @EJB
    private ProdutosNoVolumeBean produtosNoVolumeBean;

    @PostConstruct
    public void populateDB() throws MyEntityNotFoundException, MyEntityExistsException{

        userBean.create("Vasco2", "Vasco2", "vasco2@gmail.com", "123456", Role.GESTOR);
        clientBean.create("Vasco", "Vasco", "vasco@gmail.com", "123456");
        clientBean.create("maria", "Maria", "vasco@gmail.com", "123456");

        encomendaBean.create(1L, "maria");

        //volumes
        volumeBean.create(1L,  "isotérmica", 1L);
        volumeBean.create(2L,  "normal", 1L);
        volumeBean.create(3L,  "isotérmica", 1L);

        clientBean.create("joao", "joao", "joao@gmail.com", "123456");

        gestorBean.create("admin", "admin", "admin@gmail.com", "123456", true);

        logisticaBean.create("ctt", "ctt", "ctt@gmail.com", "123456");

        sensorBean.create(1L, SensorType.Temperatura, 1L);
        sensorBean.create(2L, SensorType.Pressao, 1L);
        sensorBean.create(3L, SensorType.Gps, 2L);

        dadoBean.create("25", "ta calor", 1L);
        dadoBean.create("23", "ta calor", 1L);
        dadoBean.create("26", "ta calor", 1L);


        productBean.create("Gelado de morango", "Saboroso", Category.Alimentar, 12.5);
        productBean.create("Televisão", "XYZ",Category.Eletronico, 112.99);
        productBean.create("Microondas", "ABC", Category.Eletrodomestico, 20.5);

        //produtos em Volumes
        produtosNoVolumeBean.create(1L, 5, 1L);
        produtosNoVolumeBean.create(2L, 1, 2L);
        produtosNoVolumeBean.create(3L, 3, 3L);
        produtosNoVolumeBean.create(2L, 2, 1L);

    }
}
