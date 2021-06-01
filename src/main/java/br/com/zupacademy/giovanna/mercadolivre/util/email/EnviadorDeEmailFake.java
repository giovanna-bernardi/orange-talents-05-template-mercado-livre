package br.com.zupacademy.giovanna.mercadolivre.util.email;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Component
@Primary
public class EnviadorDeEmailFake implements EnviadorDeEmail{

    @Override
    public void envia(@NotBlank String body,
                        @NotBlank String subject,
                        @NotBlank String fromName,
                        @NotBlank @Email String from,
                        @NotBlank @Email String to){
        System.out.println(body);
        System.out.println(subject);
        System.out.println(fromName);
        System.out.println(from);
        System.out.println(to);
    }


}
