package br.com.zupacademy.giovanna.mercadolivre.product.opiniao;

import br.com.zupacademy.giovanna.mercadolivre.product.opiniao.dto.OpiniaoRequest;
import br.com.zupacademy.giovanna.mercadolivre.product.produto.Produto;
import br.com.zupacademy.giovanna.mercadolivre.product.produto.ProdutoRepository;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class NovaOpiniaoController {

    private final ProdutoRepository produtoRepository;
    private final OpiniaoRepository opiniaoRepository;

    public NovaOpiniaoController(ProdutoRepository produtoRepository,
                                 OpiniaoRepository opiniaoRepository) {
        this.produtoRepository = produtoRepository;
        this.opiniaoRepository = opiniaoRepository;
    }

    @PostMapping("/produtos/{id}/opinioes")
    @Transactional
    public String cadastra(@PathVariable Long id,
                           @RequestBody @Valid OpiniaoRequest request,
                           @AuthenticationPrincipal Usuario usuarioLogado) {

        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto inexistente"));
        Opiniao opiniao = request.toModel(produto, usuarioLogado);

        opiniaoRepository.save(opiniao);
        return opiniao.toString();

    }
}
