package br.com.zupacademy.giovanna.mercadolivre.shopping;

import br.com.zupacademy.giovanna.mercadolivre.shopping.transacao.StatusTransacao;

public enum StatusRetornoPagseguro {
    SUCESSO, ERRO;

    public StatusTransacao normaliza() {
        return this.equals(SUCESSO) ? StatusTransacao.SUCESSO : StatusTransacao.ERRO;
    }
}
