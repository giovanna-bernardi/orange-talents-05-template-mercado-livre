package br.com.zupacademy.giovanna.mercadolivre.product.produto;

;
import br.com.zupacademy.giovanna.mercadolivre.imagem.dto.ImagemListaRequest;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;
import br.com.zupacademy.giovanna.mercadolivre.util.Uploader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@RestController
public class AdicionaImagensController {

    private final ProdutoRepository produtoRepository;
    private final Uploader uploaderFake;

    public AdicionaImagensController(ProdutoRepository produtoRepository, Uploader uploaderFake) {
        this.produtoRepository = produtoRepository;
        this.uploaderFake = uploaderFake;
    }

    @PostMapping("/produtos/{id}/imagens")
    @Transactional
    public ResponseEntity<?> adicionaImagens(@PathVariable Long id,
                                             @Valid ImagemListaRequest imagemListaRequest,
                                             @AuthenticationPrincipal Usuario usuarioLogado) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto inexistente"));

        // verificar se o usuário tem permissão para alterar este produto
        if(!produto.pertenceAo(usuarioLogado)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Você não pode alterar um produto que não é seu.");
        }

        // fazer o upload da imagem e receber uma url com o caminho de volta
        Set<String> urls = uploaderFake.upload(imagemListaRequest.getImagens());

        // adicionar as imagens ao produto e salvar alteração no banco
        produto.adicionaImagens(urls);
        produtoRepository.save(produto);

        return ResponseEntity.ok().body(produto.toString());
    }
}
