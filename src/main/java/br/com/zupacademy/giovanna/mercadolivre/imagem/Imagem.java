package br.com.zupacademy.giovanna.mercadolivre.imagem;

import br.com.zupacademy.giovanna.mercadolivre.product.produto.Produto;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @URL
    @NotBlank
    private String caminhoImagem;

    @ManyToOne
    @NotNull @Valid
    private Produto produto;

    @Deprecated
    public Imagem() {
    }

    public Imagem(String caminhoImagem, Produto produto) {
        this.caminhoImagem = caminhoImagem;
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Imagem{" +
                "caminhoImagem='" + caminhoImagem + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Imagem imagem = (Imagem) o;

        if (!caminhoImagem.equals(imagem.caminhoImagem)) return false;
        return produto.equals(imagem.produto);
    }

    @Override
    public int hashCode() {
        int result = caminhoImagem.hashCode();
        result = 31 * result + produto.hashCode();
        return result;
    }
}
