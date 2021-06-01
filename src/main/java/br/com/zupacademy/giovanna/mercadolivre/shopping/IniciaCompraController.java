package br.com.zupacademy.giovanna.mercadolivre.shopping;

import br.com.zupacademy.giovanna.mercadolivre.product.produto.Produto;
import br.com.zupacademy.giovanna.mercadolivre.product.produto.ProdutoRepository;
import br.com.zupacademy.giovanna.mercadolivre.shopping.dto.CompraRequest;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;
import br.com.zupacademy.giovanna.mercadolivre.util.email.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
public class IniciaCompraController {

    private final ProdutoRepository produtoRepository;
    private final CompraRepository compraRepository;
    private final Email email;



    public IniciaCompraController(ProdutoRepository produtoRepository,
                                  CompraRepository compraRepository,
                                  Email email) {
        this.produtoRepository = produtoRepository;
        this.compraRepository = compraRepository;
        this.email = email;
    }

    @PostMapping("/compras")
    public ResponseEntity<?> iniciaCompra(@RequestBody @Valid CompraRequest request,
                               @AuthenticationPrincipal Usuario usuarioLogado,
                               UriComponentsBuilder uriBuilder) throws BindException{
        Produto produtoEscolhido = produtoRepository.findById(request.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto inexistente"));

        int quantidadeEscolhida = request.getQuantidadeEscolhida();
        boolean abateuEstoque = produtoEscolhido.abateEstoque(quantidadeEscolhida);

        if(abateuEstoque) {
            // liberado para criar a compra em si
            GatewayPagamento gateway = request.getGateway();
            Compra compra = new Compra(quantidadeEscolhida, produtoEscolhido, usuarioLogado, gateway);

            compraRepository.save(compra);

            // envia e-mail ao vendedor
            email.enviaCompra(compra);

            // cria endereço de redirecionamento do gateway
            String uri = compra.urlRedirecionamento(uriBuilder);
            return ResponseEntity.ok().location(URI.create(uri)).body(compra.toString());
//            return compra.urlRedirecionamento(uriBuilder);

        }

        // Não abateu estoque
        BindException problemaComEstoque = new BindException(request,
                "compraRequest");
        problemaComEstoque.reject(null,
                "Não foi possível realizar a compra por conta do estoque");

        throw problemaComEstoque;
    }


}
