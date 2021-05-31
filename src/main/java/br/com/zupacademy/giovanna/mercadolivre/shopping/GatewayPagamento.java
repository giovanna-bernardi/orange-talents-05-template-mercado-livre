package br.com.zupacademy.giovanna.mercadolivre.shopping;

import org.springframework.web.util.UriComponentsBuilder;


public enum GatewayPagamento {
    PAYPAL {
        @Override
        String criaUrlRetorno(Compra compra,
                              UriComponentsBuilder uriComponentsBuilder) {

            String urlRetornoPaypal = uriComponentsBuilder
                    .path("/retorno-paypal/{id}").buildAndExpand(compra.getId())
                    .toString();

            return "paypal.com?buyerId=" + compra.getId() + "&redirectUrl=" + urlRetornoPaypal;
        }
    },
    PAGSEGURO {
        @Override
        String criaUrlRetorno(Compra compra,
                              UriComponentsBuilder uriComponentsBuilder) {


            String urlRetornoPagseguro = uriComponentsBuilder
                    .path("/retorno-pagseguro/{id}")
                    .buildAndExpand(compra.getId()).toString();

            return "pagseguro.com?buyerId=" + compra.getId() + "&redirectUrl="
                    + urlRetornoPagseguro;
        }
    };

    abstract String criaUrlRetorno(Compra compra,
                                   UriComponentsBuilder uriComponentsBuilder);
}
