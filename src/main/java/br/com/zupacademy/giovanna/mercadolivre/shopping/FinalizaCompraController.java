package br.com.zupacademy.giovanna.mercadolivre.shopping;

import br.com.zupacademy.giovanna.mercadolivre.otherSystems.NotaFiscal;
import br.com.zupacademy.giovanna.mercadolivre.otherSystems.Ranking;
import br.com.zupacademy.giovanna.mercadolivre.shopping.dto.PagseguroRequest;
import br.com.zupacademy.giovanna.mercadolivre.shopping.dto.PaypalRequest;
import br.com.zupacademy.giovanna.mercadolivre.shopping.dto.RetornoGatewayPagamento;
import br.com.zupacademy.giovanna.mercadolivre.shopping.evento.EventosNovaCompra;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class FinalizaCompraController {

    private final CompraRepository compraRepository;
    private final NotaFiscal notaFiscal;
    private final Ranking ranking;
    private final EventosNovaCompra eventosNovaCompra;

    public FinalizaCompraController(CompraRepository compraRepository,
                                    NotaFiscal notaFiscal,
                                    Ranking ranking,
                                    EventosNovaCompra eventosNovaCompra) {
        this.compraRepository = compraRepository;
        this.notaFiscal = notaFiscal;
        this.ranking = ranking;
        this.eventosNovaCompra = eventosNovaCompra;
    }

    @PostMapping("/retorno-pagseguro/{id}")
    @Transactional
    public String processamentoPagSeguro(@PathVariable("id") Long compraId,
                                         @Valid PagseguroRequest request) {
        // não vai ser por json, vai ser um Form URL encoder
        return processa(compraId, request);
    }

    @PostMapping("/retorno-paypal/{id}")
    @Transactional
    public String processamentoPayPal(@PathVariable("id") Long compraId,
                                      @Valid PaypalRequest request) {
        return processa(compraId, request);
    }

    private String processa(Long compraId, RetornoGatewayPagamento request) {
        Compra compra = compraRepository.findById(compraId)
                .orElseThrow(() -> new IllegalArgumentException("Compra inexistente"));

        compra.adicionaTransacao(request);
        compraRepository.save(compra);

        eventosNovaCompra.processa(compra);

        return compra.toString();
    }
}
