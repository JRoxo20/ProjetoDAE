package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Category;


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
        private VolumeBean volumeBean;

        @EJB
        private ProductBean productBean;

        @PostConstruct
        public void populateDB() {

            userBean.create("Vasco", "Vasco", "vasco@gmail.com", "123456");

            clientBean.create("joao", "joao", "joao@gmail.com", "123456");

            gestorBean.create("admin", "admin", "admin@gmail.com", "123456", true);

            logisticaBean.create("ctt", "ctt", "ctt@gmail.com", "123456");

            encomendaBean.create(1, "joao");


            //volumes
            volumeBean.create(1L, "isotérmica");
            volumeBean.create(2L, "normal");
            volumeBean.create(3L, "isotérmica");

            productBean.create("Gelado de morango", "Saboroso",1, Category.Alimentar, 12.5);
            productBean.create("Televisão", "XYZ",1, Category.Eletronico, 112.99);
            productBean.create("Microondas", "ABC",1, Category.Eletrodomestico, 20.5);
        }
    }

