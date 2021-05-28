package br.com.zupacademy.giovanna.mercadolivre.product.produto.dto;

import br.com.zupacademy.giovanna.mercadolivre.product.caracteristica.dto.CaracteristicaRequest;
import br.com.zupacademy.giovanna.mercadolivre.product.categoria.Categoria;
import br.com.zupacademy.giovanna.mercadolivre.product.produto.Produto;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;
import br.com.zupacademy.giovanna.mercadolivre.validation.ExistsId;
import br.com.zupacademy.giovanna.mercadolivre.validation.UniqueValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.*;

public class ProdutoRequest {
    
    @NotBlank
    @UniqueValue(domainClass = Produto.class, fieldName = "nome")
    private String nome;
    
    @NotNull @Positive
    private BigDecimal preco;
    
    @NotNull @Positive
    private int quantidadeDisponivel;
    
    @NotBlank @Length(max = 1000)
    private String descricao;

    @NotNull
    @ExistsId(domainClass = Categoria.class, fieldName = "id")
    private Long categoriaId;

    @NotNull @Size(min = 3) @Valid
    private List<CaracteristicaRequest> caracteristicas;

    public ProdutoRequest( @NotBlank String nome,
                          @NotNull @Positive BigDecimal preco,
                          @NotNull @Positive int quantidadeDisponivel,
                          @NotBlank @Length(max = 1000) String descricao,
                          @NotNull Long categoriaId,
                          @NotNull @Size(min = 3) @Valid List<CaracteristicaRequest> caracteristicas) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.descricao = descricao;
        this.categoriaId = categoriaId;
        this.caracteristicas =caracteristicas;
    }

    public Produto toModel(Categoria categoria, Usuario dono) {
        return new Produto(nome, preco, quantidadeDisponivel, descricao, categoria, dono, caracteristicas);
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public List<CaracteristicaRequest> getCaracteristicas() {
        return caracteristicas;
    }

    public boolean caracteristicaTemMesmoNome() {
        Set<String> nomesIguais = new HashSet<>();

        for (CaracteristicaRequest caracteristica: caracteristicas) {
            if(!nomesIguais.add(caracteristica.getNome().toUpperCase())) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ProdutoRequest{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidadeDisponivel=" + quantidadeDisponivel +
                ", descricao='" + descricao + '\'' +
                ", categoriaId=" + categoriaId +
                '}';
    }
}
