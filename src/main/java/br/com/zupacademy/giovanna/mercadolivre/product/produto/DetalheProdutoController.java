package br.com.zupacademy.giovanna.mercadolivre.product.produto;

import br.com.zupacademy.giovanna.mercadolivre.product.pergunta.PerguntaRepository;
import br.com.zupacademy.giovanna.mercadolivre.product.pergunta.dto.PerguntaRequest;
import br.com.zupacademy.giovanna.mercadolivre.product.produto.dto.ProdutoDetalheResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DetalheProdutoController {

    private final ProdutoRepository produtoRepository;
    private final PerguntaRepository perguntaRepository;

    public DetalheProdutoController(ProdutoRepository produtoRepository,
                                    PerguntaRepository perguntaRepository) {
        this.produtoRepository = produtoRepository;
        this.perguntaRepository = perguntaRepository;
    }

    @GetMapping("/produtos/{id}")
    public ProdutoDetalheResponse detalhaProduto(@PathVariable Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto inexistente"));
        return new ProdutoDetalheResponse(produto, perguntaRepository);
    }
}
