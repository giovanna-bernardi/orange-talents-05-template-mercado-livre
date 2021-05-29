package br.com.zupacademy.giovanna.mercadolivre.util;

import br.com.zupacademy.giovanna.mercadolivre.product.pergunta.Pergunta;
import org.springframework.stereotype.Component;

@Component
public class Email {

    private final EnviadorDeEmail enviadorDeEmail;

    public Email(EnviadorDeEmail enviadorDeEmail) {
        this.enviadorDeEmail = enviadorDeEmail;
    }

    public void enviaPergunta(Pergunta pergunta){
        enviadorDeEmail.envia(
                "<html>...</html>",
                pergunta.getTitulo(),
                pergunta.getEmailDoDonoDaPergunta(),
                "novapergunta@nossomercadolivre.com",
                pergunta.getEmailDoVendedorDoProduto()
        );
    }
}
