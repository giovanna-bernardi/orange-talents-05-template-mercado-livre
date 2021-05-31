package br.com.zupacademy.giovanna.mercadolivre.product.imagem;

import br.com.zupacademy.giovanna.mercadolivre.product.produto.Produto;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class ImagemProduto {

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
    public ImagemProduto() {
    }

    public ImagemProduto(String caminhoImagem, Produto produto) {
        this.caminhoImagem = caminhoImagem;
        this.produto = produto;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
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

        ImagemProduto imagemProduto = (ImagemProduto) o;

        if (!caminhoImagem.equals(imagemProduto.caminhoImagem)) return false;
        return produto.equals(imagemProduto.produto);
    }

    @Override
    public int hashCode() {
        int result = caminhoImagem.hashCode();
        result = 31 * result + produto.hashCode();
        return result;
    }
}
