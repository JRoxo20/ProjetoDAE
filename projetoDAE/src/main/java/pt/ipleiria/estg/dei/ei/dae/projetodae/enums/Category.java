package pt.ipleiria.estg.dei.ei.dae.projetodae.enums;

import com.opencsv.bean.AbstractBeanField;

public enum Category {
    ALIMENTAR,
    ELECTRODOMESTICO,
    ELETRONICO;

    public static Category convert(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        switch (value.trim().toUpperCase()) {
            case "ELETRONICO":
                return ELETRONICO;
            case "ELECTRODOMESTICO":
                return ELECTRODOMESTICO;
            case "ALIMENTAR":
                return ALIMENTAR;
            default:
                throw new IllegalArgumentException("Invalid category value: " + value);
        }
    }
    public static class CategoryConverter extends AbstractBeanField<Category, String> {
        @Override
        protected Category convert(String value) {
            return Category.convert(value);  // Call the static convert method
        }
    }
}
