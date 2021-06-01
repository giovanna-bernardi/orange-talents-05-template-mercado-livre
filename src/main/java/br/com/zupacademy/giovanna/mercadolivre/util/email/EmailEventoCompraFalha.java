package br.com.zupacademy.giovanna.mercadolivre.util.email;

import br.com.zupacademy.giovanna.mercadolivre.shopping.evento.EventoCompraFalha;
import br.com.zupacademy.giovanna.mercadolivre.shopping.Compra;
import org.springframework.stereotype.Component;

@Component
public class EmailEventoCompraFalha implements EventoCompraFalha {

    private final EnviadorDeEmail enviadorDeEmail;

    public EmailEventoCompraFalha(EnviadorDeEmail enviadorDeEmail) {
        this.enviadorDeEmail = enviadorDeEmail;
    }

    @Override
    public void processa(Compra compra) {
        enviadorDeEmail.envia(
                "<html> Falha no pagamento! Por favor, tente de novo." +
                        "<a href=\"http://localhost:8080/compras\">Tentar novamente</a></html>",
                "Compra NÃO concluída com sucesso",
                "Mercado Livre",
                "pagamentofalha@nossomercadolivre.com",
                compra.getEmailDoComprador()
        );
    }
}
