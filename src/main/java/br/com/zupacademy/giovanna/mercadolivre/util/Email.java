package br.com.zupacademy.giovanna.mercadolivre.util;

import br.com.zupacademy.giovanna.mercadolivre.product.pergunta.Pergunta;
import org.springframework.stereotype.Component;

@Component
public class Email {

    private final EnviadorDeEmail enviadorDeEmailFake;

    public Email(EnviadorDeEmail enviadorDeEmailFake) {
        this.enviadorDeEmailFake = enviadorDeEmailFake;
    }

    public String enviaPergunta(Pergunta pergunta){
        return enviadorDeEmailFake.envia(pergunta);
    }
}
