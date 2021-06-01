package br.com.zupacademy.giovanna.mercadolivre.shopping.evento;

import br.com.zupacademy.giovanna.mercadolivre.shopping.Compra;

/**
 * Todo evento relacionado ao sucesso de uma nova compra deve implementar esta interface.
 * */
public interface EventoCompraSucesso {
    void processa(Compra compra);
}
