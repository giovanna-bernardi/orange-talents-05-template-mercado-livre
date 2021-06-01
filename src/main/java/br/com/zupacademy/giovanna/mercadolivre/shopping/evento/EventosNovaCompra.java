package br.com.zupacademy.giovanna.mercadolivre.shopping.evento;

import br.com.zupacademy.giovanna.mercadolivre.shopping.Compra;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class EventosNovaCompra {

    private final Set<EventoCompraSucesso> eventosCompraSucesso;
    private final Set<EventoCompraFalha> eventosCompraFalha;

    public EventosNovaCompra(Set<EventoCompraSucesso> eventosCompraSucesso,
                             Set<EventoCompraFalha> eventosCompraFalha) {
        this.eventosCompraSucesso = eventosCompraSucesso;
        this.eventosCompraFalha = eventosCompraFalha;
    }

    public void processa(Compra compra) {
        if (compra.processadaComSucesso()) {
//            // Comunicar com sistema de Nota Fiscal
//            notaFiscal.processa(compra);
//            // Comunicar com sistema de Ranking de Vendedores
//            ranking.processa(compra);
//            // Enviar e-mail para o comprador
//            email.enviaPagamentoSucesso(compra);
            eventosCompraSucesso.forEach(evento -> evento.processa(compra));
        } else {
            eventosCompraFalha.forEach(evento -> evento.processa(compra));
        }
    }
}
