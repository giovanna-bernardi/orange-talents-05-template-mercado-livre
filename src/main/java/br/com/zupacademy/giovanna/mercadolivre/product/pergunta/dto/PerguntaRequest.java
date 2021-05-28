package br.com.zupacademy.giovanna.mercadolivre.product.pergunta.dto;

import br.com.zupacademy.giovanna.mercadolivre.product.pergunta.Pergunta;
import br.com.zupacademy.giovanna.mercadolivre.product.produto.Produto;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;

import javax.validation.constraints.NotBlank;

public class PerguntaRequest {

    @NotBlank
    private String titulo;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Pergunta toModel(Produto produto, Usuario donoDaPergunta) {
        return new Pergunta(this.titulo, produto, donoDaPergunta);
    }
}
