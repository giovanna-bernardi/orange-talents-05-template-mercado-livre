package br.com.zupacademy.giovanna.mercadolivre.util.email;

import br.com.zupacademy.giovanna.mercadolivre.shopping.evento.EventoCompraSucesso;
import br.com.zupacademy.giovanna.mercadolivre.shopping.Compra;
import org.springframework.stereotype.Component;

@Component
public class EmailEventoCompraSucesso implements EventoCompraSucesso {
    private final EnviadorDeEmail enviadorDeEmail;

    public EmailEventoCompraSucesso(EnviadorDeEmail enviadorDeEmail) {
        this.enviadorDeEmail = enviadorDeEmail;
    }

    @Override
    public void processa(Compra compra) {
        enviadorDeEmail.envia(
                "<html> " + compra + " </html>",
                "Pagamento realizado com sucesso",
                "Mercado Livre",
                "pagamentosucesso@nossomercadolivre.com",
                compra.getEmailDoComprador()
        );
    }
}
