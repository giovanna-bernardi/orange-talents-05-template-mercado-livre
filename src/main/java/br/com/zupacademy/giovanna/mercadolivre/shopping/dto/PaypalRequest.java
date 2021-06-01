package br.com.zupacademy.giovanna.mercadolivre.shopping.dto;

import br.com.zupacademy.giovanna.mercadolivre.shopping.Compra;
import br.com.zupacademy.giovanna.mercadolivre.shopping.transacao.StatusTransacao;
import br.com.zupacademy.giovanna.mercadolivre.shopping.transacao.Transacao;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PaypalRequest implements RetornoGatewayPagamento {

    @Min(0) @Max(1)
    private int status;

    @NotBlank
    private String transacaoId;

    public PaypalRequest(@Min(0) @Max(1) int status, @NotBlank String transacaoId) {
        this.status = status;
        this.transacaoId = transacaoId;
    }

    @Override
    public Transacao toTransacao(Compra compra) {
        return new Transacao(
                this.status == 0 ? StatusTransacao.ERRO : StatusTransacao.SUCESSO,
                this.transacaoId, compra);
    }
}
