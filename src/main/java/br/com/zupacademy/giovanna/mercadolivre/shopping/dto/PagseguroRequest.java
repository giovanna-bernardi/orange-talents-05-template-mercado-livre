package br.com.zupacademy.giovanna.mercadolivre.shopping.dto;

import br.com.zupacademy.giovanna.mercadolivre.shopping.Compra;
import br.com.zupacademy.giovanna.mercadolivre.shopping.StatusRetornoPagseguro;
import br.com.zupacademy.giovanna.mercadolivre.shopping.transacao.Transacao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PagseguroRequest implements RetornoGatewayPagamento {

    @NotBlank
    private String transacaoId;

    @NotNull
    private StatusRetornoPagseguro status;

    public PagseguroRequest(@NotBlank String transacaoId,
                            @NotNull StatusRetornoPagseguro status) {
        this.transacaoId = transacaoId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "PagseguroRequest{" +
                "transacaoId=" + transacaoId +
                ", status=" + status +
                '}';
    }

    @Override
    public Transacao toTransacao(Compra compra) {
        return new Transacao(status.normaliza(), this.transacaoId, compra);
    }
}
