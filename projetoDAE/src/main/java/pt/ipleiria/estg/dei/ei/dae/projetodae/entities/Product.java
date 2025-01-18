
package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


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
    private Category category;
    @NotNull
    private Double price;
    @ManyToMany
    private List<Volume> volumes;
    @OneToMany(mappedBy = "produto")
    private List<ProdutosNoVolume> produtosNoVolume;


/*    @Version
    private int version;*/

    public Product() {
        this.volumes = new ArrayList<>();
        this.produtosNoVolume = new ArrayList<>();
    }

    public Product( String name,String brand, Category category, Double price) {
        this.brand = brand;
        this.name = name;
        this.category = category;
        this.price = price;
        this.volumes = new ArrayList<>();
        this.produtosNoVolume = new ArrayList<>();
    }


    public List<ProdutosNoVolume> getProdutosNoVolume() {
        return produtosNoVolume;
    }

    public void setProdutosNoVolume(List<ProdutosNoVolume> produtosNoVolume) {
        this.produtosNoVolume = produtosNoVolume;
    }

    public void addProdutosNoVolume(ProdutosNoVolume novoProdutosNoVolume)
    {
        if (!produtosNoVolume.contains(novoProdutosNoVolume))
        {
            produtosNoVolume.add(novoProdutosNoVolume);
        }
    }

    public void removeProdutosNoVolume(ProdutosNoVolume novoProdutosNoVolume)
    {
        produtosNoVolume.remove(novoProdutosNoVolume);
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
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

