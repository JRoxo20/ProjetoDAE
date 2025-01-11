package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Product;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO implements Serializable {

    private Long id_product;
    private String name;
    private String brand;
    private int quantity;
    private String category;
    private Double price;

    public ProductDTO(){

    }

    public ProductDTO(Long id,String name,String brand, int quantity, String category, Double price) {
        this.id_product=id;
        this.name = name;
        this.brand = brand;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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
                product.getQuantity(),
                product.getCategory(),
                product.getPrice()
        );
    }

    // converts an entire list of entities into a list of DTOs
    public static List<ProductDTO> from(List<Product> productList) {
        return productList.stream().map(ProductDTO::from).collect(Collectors.toList());
    }
    
}
