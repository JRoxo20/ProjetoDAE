package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.opencsv.bean.CsvCustomBindByName;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDTO implements Serializable {

    private Long id_product;


    @CsvBindByName(column = "name")
    @CsvBindByPosition(position = 0)
    private String name;

    @CsvBindByName(column = "brand")
    @CsvBindByPosition(position = 1)
    private String brand;

    @CsvCustomBindByName(converter = Category.CategoryConverter.class)
    @CsvBindByPosition(position = 2)
    private Category category;

    @CsvBindByName(column = "price")
    @CsvBindByPosition(position = 3)
    private Double price;


    private List<ProductDTO> listProducts;

    public ProductDTO() {
        // Default constructor
        listProducts = new ArrayList<>();
    }


    public ProductDTO(Long id,String name,String brand, Category category, Double price) {
        this.id_product=id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
    }

    public ProductDTO(String name,String brand, Category category, Double price) {
        this.name = name;
        this.brand = brand;
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
                product.getPrice()
        );
    }

    public static ProductDTO fromSemId(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getBrand(),
                product.getCategory(),
                product.getPrice()
        );
    }



    // converts an entire list of entities into a list of DTOs
    public static List<ProductDTO> from(List<Product> productList) {
        return productList.stream().map(ProductDTO::from).collect(Collectors.toList());
    }
    
}
