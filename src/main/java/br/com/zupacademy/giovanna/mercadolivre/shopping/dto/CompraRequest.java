package br.com.zupacademy.giovanna.mercadolivre.shopping.dto;

import br.com.zupacademy.giovanna.mercadolivre.shopping.GatewayPagamento;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CompraRequest {

    @Positive
    private int quantidadeEscolhida;

    @NotNull
    private Long produtoId;

    @NotNull
    private GatewayPagamento gateway;

    public CompraRequest(@Positive int quantidadeEscolhida,
                         @NotNull Long produtoId,
                         @NotNull GatewayPagamento gateway) {
        this.quantidadeEscolhida = quantidadeEscolhida;
        this.produtoId = produtoId;
        this.gateway = gateway;
    }

    public int getQuantidadeEscolhida() {
        return quantidadeEscolhida;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public GatewayPagamento getGateway() {
        return gateway;
    }
}
