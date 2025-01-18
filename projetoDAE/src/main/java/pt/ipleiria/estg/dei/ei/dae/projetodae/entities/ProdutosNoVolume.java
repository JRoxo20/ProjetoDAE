package pt.ipleiria.estg.dei.ei.dae.projetodae.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "produtosNoVolume")
//@NamedQuery(name = "getAllProducts", query = "SELECT p FROM Product p ORDER BY p.id_product" )
public class ProdutosNoVolume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    private Product produto;
    private int quantidade;
    @ManyToOne
    private Volume volume;


    public ProdutosNoVolume() {
    }

    public ProdutosNoVolume(Product produto, int quantidade, Volume volume) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.volume = volume;
    }


    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Product getProduto() {
        return produto;
    }

    public void setProduto(Product produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
