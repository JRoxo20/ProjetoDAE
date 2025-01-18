package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import pt.ipleiria.estg.dei.ei.dae.projetodae.ejbs.ProdutosNoVolumeBean;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.ProdutosNoVolume;
import pt.ipleiria.estg.dei.ei.dae.projetodae.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.projetodae.enums.Category;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutosNoVolumeDTO implements Serializable {
    private Long id_produto;
    private String nome_produto;
    private String brand;
    private Category category;
    private int quantidade;
    private Double price;

    public ProdutosNoVolumeDTO(){

    }

    public ProdutosNoVolumeDTO(Long id_produto, int quantidade) {
        this.id_produto = id_produto;
        this.quantidade = quantidade;
    }

    public ProdutosNoVolumeDTO(Long id_produto, String nome_produto, Category category, int quantidade, String brand, Double price) {
        this.id_produto = id_produto;
        this.nome_produto = nome_produto;
        this.category = category;
        this.quantidade = quantidade;
        this.brand = brand;
        this.price = price;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getId_produto() {
        return id_produto;
    }

    public void setId_produto(Long id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // Convert Entity to DTO
    public static ProdutosNoVolumeDTO from(ProdutosNoVolume product) {
        return new ProdutosNoVolumeDTO(
                product.getProduto().getId(),
                product.getProduto().getName(),
                product.getProduto().getCategory(),
                product.getQuantidade(),
                product.getProduto().getBrand(),
                product.getProduto().getPrice()


        );
    }

    public static ProdutosNoVolumeDTO createPfrom(ProdutosNoVolume product) {
        return new ProdutosNoVolumeDTO(
                product.getProduto().getId(),
                product.getQuantidade()


        );
    }

    // converts an entire list of entities into a list of DTOs
    public static List<ProdutosNoVolumeDTO> from(List<ProdutosNoVolume> productList) {
        return productList.stream().map(ProdutosNoVolumeDTO::from).collect(Collectors.toList());
    }
}
