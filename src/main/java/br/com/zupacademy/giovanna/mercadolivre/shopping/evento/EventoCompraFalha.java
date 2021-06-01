package br.com.zupacademy.giovanna.mercadolivre.shopping.evento;

import br.com.zupacademy.giovanna.mercadolivre.shopping.Compra;

/**
 * Todo evento relacionado à falha de uma nova compra deve implementar esta interface.
 * */
public interface EventoCompraFalha {
    void processa(Compra compra);
}

