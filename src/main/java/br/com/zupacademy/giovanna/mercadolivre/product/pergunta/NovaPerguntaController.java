package br.com.zupacademy.giovanna.mercadolivre.product.pergunta;

import br.com.zupacademy.giovanna.mercadolivre.product.pergunta.dto.PerguntaRequest;
import br.com.zupacademy.giovanna.mercadolivre.product.produto.Produto;
import br.com.zupacademy.giovanna.mercadolivre.product.produto.ProdutoRepository;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;
import br.com.zupacademy.giovanna.mercadolivre.util.Email;
import br.com.zupacademy.giovanna.mercadolivre.util.EnviadorDeEmail;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class NovaPerguntaController {

    private final ProdutoRepository produtoRepository;
    private final PerguntaRepository perguntaRepository;
    private final Email email;

    public NovaPerguntaController(ProdutoRepository produtoRepository,
                                  PerguntaRepository perguntaRepository,
                                  Email email) {
        this.produtoRepository = produtoRepository;
        this.perguntaRepository = perguntaRepository;
        this.email = email;
    }

    @PostMapping("/produtos/{id}/perguntas")
    @Transactional
    public String criaPergunta(@PathVariable Long id,
                               @RequestBody @Valid PerguntaRequest request,
                               @AuthenticationPrincipal Usuario usuarioLogado) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto inexistente"));

        Pergunta novaPergunta = request.toModel(produto, usuarioLogado);
        perguntaRepository.save(novaPergunta);

        // se a pergunta foi salva com sucesso
        if(novaPergunta.getId() != null) {
            // envia e-mail ao vendedor
            email.enviaPergunta(novaPergunta);
            return novaPergunta.toString();
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
