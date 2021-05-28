package br.com.zupacademy.giovanna.mercadolivre.product.opiniao.dto;

import br.com.zupacademy.giovanna.mercadolivre.product.opiniao.Opiniao;
import br.com.zupacademy.giovanna.mercadolivre.product.produto.Produto;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;

import javax.validation.constraints.*;

public class OpiniaoRequest {

    @NotNull @Min(1) @Max(5)
    private int nota;

    @NotBlank
    private String titulo;

    @NotBlank @Size(max = 500)
    private String descricao;

    public OpiniaoRequest(@NotNull @Min(1) @Max(5) int nota,
                          @NotBlank String titulo,
                          @NotBlank @Size(max = 500) String descricao) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Opiniao toModel(Produto produto, Usuario consumidor) {
        return new Opiniao(this.nota, this.titulo, this.descricao, produto, consumidor);
    }
}
