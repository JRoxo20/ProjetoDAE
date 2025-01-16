package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Category;

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
        private VolumeBean volumeBean;

        @EJB
        private ProductBean productBean;

        @PostConstruct
        public void populateDB() {

            userBean.create("Vasco1", "Vasco", "vasco@gmail.com", "123456");
            clientBean.create("Vasco", "Vasco", "Vasco32@gmail.com", "123456");

            //volumes


            encomendaBean.create("Vasco");
            encomendaBean.enrollVolumeInEncomenda(1L, 1L);

            volumeBean.create(1L, "pendente", "caixa", 1L);
            volumeBean.create(2L, "pendente", "caixa", 1L);

            clientBean.create("joao", "joao", "joao@gmail.com", "123456");

            gestorBean.create("admin", "admin", "admin@gmail.com", "123456", true);

            logisticaBean.create("ctt", "ctt", "ctt@gmail.com", "123456");


            productBean.create("Gelado de morango", "Saboroso", Category.Alimentar, 12.5);
            productBean.create("Televisão", "XYZ",Category.Eletronico, 112.99);
            productBean.create("Microondas", "ABC", Category.Eletrodomestico, 20.5);
        }
    }

