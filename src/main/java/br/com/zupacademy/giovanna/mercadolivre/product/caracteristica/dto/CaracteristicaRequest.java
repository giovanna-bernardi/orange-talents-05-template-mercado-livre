package br.com.zupacademy.giovanna.mercadolivre.product.caracteristica.dto;

import br.com.zupacademy.giovanna.mercadolivre.product.caracteristica.Caracteristica;
import br.com.zupacademy.giovanna.mercadolivre.product.produto.Produto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CaracteristicaRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    public CaracteristicaRequest(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Caracteristica toModel(@NotNull @Valid Produto produto) {
        return new Caracteristica(this.nome, this.descricao, produto);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CaracteristicaRequest that = (CaracteristicaRequest) o;

        if (!nome.equals(that.nome)) return false;
        return descricao.equals(that.descricao);
    }

    @Override
    public int hashCode() {
        int result = nome.hashCode();
        result = 31 * result + descricao.hashCode();
        return result;
    }
}
