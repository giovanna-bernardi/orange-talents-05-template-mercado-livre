package br.com.zupacademy.giovanna.mercadolivre.util;

import br.com.zupacademy.giovanna.mercadolivre.product.pergunta.Pergunta;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EnviadorDeEmailFake implements EnviadorDeEmail{
    @Override
    public String envia(Pergunta pergunta) {
        return "Enviando e-mail para '" + pergunta.getEmailDoVendedorDoProduto() +
                "' com a pergunta '" + pergunta.getTitulo() +
                "' do usuário com e-mail '" + pergunta.getEmailDoDonoDaPergunta() + "'";
    }
}
