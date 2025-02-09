package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.SensorType;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Category;
import pt.ipleiria.estg.dei.ei.dae.projetodae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projetodae.exceptions.MyEntityNotFoundException;



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
        
        clientBean.create("Vasco", "Vasco", "vasco@gmail.com", "123456");
        clientBean.create("maria", "Maria", "maria@gmail.com", "123456");

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
        sensorBean.create(4L, SensorType.Aceleracao, 2L);

        dadoBean.create( "25ºC", "Temperatura", 1L);
        dadoBean.create( "28ºC", "Temperatura", 1L);
        dadoBean.create( "22ºC", "Temperatura", 1L);

        dadoBean.create( "1.2", "Pressão", 2L);
        dadoBean.create( "1.3", "Pressão", 2L);
        dadoBean.create( "1.1", "Pressão", 2L);

        dadoBean.create( "39.734305, -8.821126", "Latitude Longitude", 3L);
        dadoBean.create( "39.598330, -9.072654", "Latitude Longitude", 3L);

        productBean.create("Gelado de morango", "Saboroso", Category.ALIMENTAR, 12.5);
        productBean.create("Televisão", "XYZ",Category.ELETRONICO, 112.99);
        productBean.create("Microondas", "ABC", Category.ELECTRODOMESTICO, 20.5);

        //produtos em Volumes
        produtosNoVolumeBean.create(1L, 5, 1L);
        produtosNoVolumeBean.create(2L, 1, 2L);
        produtosNoVolumeBean.create(3L, 3, 3L);
        produtosNoVolumeBean.create(2L, 2, 1L);

    }
}
