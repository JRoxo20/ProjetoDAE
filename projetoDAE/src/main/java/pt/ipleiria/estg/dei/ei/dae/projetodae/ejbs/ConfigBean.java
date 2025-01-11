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
            try {
                userBean.create("Vasco", "Vasco", "vasco@gmail.com", "123456");

                productBean.create("Gelado Baunilha", "ABC",1, "Alimentar", 2.99);
                productBean.create("Microondas", "XYZ",1, "Eletrodomestico", 12.99);
                productBean.create("Ferro", "XYZ",1, "Eletrodomestico", 22.99);
            } catch (Exception e) {
                throw new RuntimeException("Error populating the database", e);
            }
        }

    }

