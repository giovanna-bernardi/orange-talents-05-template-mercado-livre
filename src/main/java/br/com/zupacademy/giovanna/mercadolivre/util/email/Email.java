package br.com.zupacademy.giovanna.mercadolivre.util.email;

import br.com.zupacademy.giovanna.mercadolivre.product.pergunta.Pergunta;
import br.com.zupacademy.giovanna.mercadolivre.shopping.Compra;
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

    public void enviaCompra(Compra compra){
        enviadorDeEmail.envia(
                "<html>...</html>",
                "Nova compra realizada",
                compra.getEmailDoComprador(),
                "comprarealizada@nossomercadolivre.com",
                compra.getEmailDoVendedorDoProduto()
        );
    }

}
