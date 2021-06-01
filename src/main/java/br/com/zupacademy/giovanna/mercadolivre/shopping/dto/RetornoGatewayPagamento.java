package br.com.zupacademy.giovanna.mercadolivre.shopping.dto;

import br.com.zupacademy.giovanna.mercadolivre.shopping.Compra;
import br.com.zupacademy.giovanna.mercadolivre.shopping.transacao.Transacao;

public interface RetornoGatewayPagamento {
    /**
     *
     * @param compra
     * @return uma nova transação em função do gateway específico
     */
    Transacao toTransacao(Compra compra);
}
