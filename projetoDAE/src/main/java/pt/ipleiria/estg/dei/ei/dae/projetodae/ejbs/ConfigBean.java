package pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;



    @Startup
    @Singleton
    public class ConfigBean {
        @EJB
        private UserBean userBean;

        @EJB
        private ProductBean productBean;
        @PostConstruct
        public void populateDB() {
            userBean.create("Vasco", "Vasco", "vasco@gmail.com", "123456");
            productBean.create("Gelado Baunilha",1,"Alimentar",2.99);
            productBean.create("Microondas",1,"Eletrodomestico",12.99);
            productBean.create("Ferro",1,"Eletrodomestico",22.99);

        }
    }

