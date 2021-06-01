package br.com.zupacademy.giovanna.mercadolivre.util.email;

import br.com.zupacademy.giovanna.mercadolivre.product.pergunta.Pergunta;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;

public interface EnviadorDeEmail {

    void envia(@NotBlank String body,
                 @NotBlank String subject,
                 @NotBlank String fromName,
                 @NotBlank @Email String from,
                 @NotBlank @Email String to);
}
