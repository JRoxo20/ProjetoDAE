package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Category;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO implements Serializable {

    private Long id_product;
    private String name;
    private String brand;
    private Category category;
    private Double price;
    private Long volume_id;

    public ProductDTO(){

    }

    public ProductDTO(Long id,String name,String brand, Category category, Double price, Long volume_id) {
        this.id_product=id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.volume_id = volume_id;
    }


    public Long getVolume_id() {
        return volume_id;
    }

    public void setVolume_id(Long volume_id) {
        this.volume_id = volume_id;
    }

    public Long getId() {
        return id_product;
    }

    public void setId(Long id) {
        this.id_product = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // Convert Entity to DTO
    public static ProductDTO from(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getCategory(),
                product.getPrice(),
                product.getVolume().getId()
        );
    }

    // converts an entire list of entities into a list of DTOs
    public static List<ProductDTO> from(List<Product> productList) {
        return productList.stream().map(ProductDTO::from).collect(Collectors.toList());
    }
    
}
