
package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Category;

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
    private String brand;
    @NotNull
    private int quantity;
    @NotNull
    private Category category;
    @NotNull
    private Double price;


/*    @Version
    private int version;*/

    public Product() {
    }

    public Product( String name,String brand, int quantity, Category category, Double price) {
        this.brand = brand;
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

    public  String getBrand() {
        return brand;
    }

    public void setBrand( String brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public  Category getCategory() {
        return category;
    }

    public void setCategory( Category category) {
        this.category = category;
    }

    public  Double getPrice() {
        return price;
    }

    public void setPrice( Double price) {
        this.price = price;
    }
}

