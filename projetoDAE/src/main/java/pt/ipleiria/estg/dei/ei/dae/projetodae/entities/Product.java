
package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;


@Entity
@Table(name = "products")
@NamedQuery(name = "getAllProducts", query = "SELECT p FROM Product p ORDER BY p.id_product" )
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_product;
    @NotNull
    private String name;
    @NotNull
    private int quantity;
    @NotNull
    private String category;
    @NotNull
    private Double price;

/*    @Version
    private int version;*/

    public Product() {
    }

    public Product( String name, int quantity, String category, Double price) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
    }

    public Long getId() {
        return id_product;
    }

    public void setId(Long id) {
        this.id_product = id;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public  String getCategory() {
        return category;
    }

    public void setCategory( String category) {
        this.category = category;
    }

    public  Double getPrice() {
        return price;
    }

    public void setPrice( Double price) {
        this.price = price;
    }
}

