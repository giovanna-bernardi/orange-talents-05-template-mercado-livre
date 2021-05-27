package br.com.zupacademy.giovanna.mercadolivre.product.produto;

import br.com.zupacademy.giovanna.mercadolivre.product.categoria.Categoria;
import br.com.zupacademy.giovanna.mercadolivre.product.categoria.CategoriaRepository;
import br.com.zupacademy.giovanna.mercadolivre.product.produto.dto.ProdutoRequest;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;
import br.com.zupacademy.giovanna.mercadolivre.user.UsuarioRepository;
import br.com.zupacademy.giovanna.mercadolivre.validation.NomeNaoPodeSerIgualValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class NovoProdutoController {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new NomeNaoPodeSerIgualValidator());
    }

    public NovoProdutoController(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping("/produtos")
    @Transactional
    public String cadastra(@RequestBody @Valid ProdutoRequest request,
                           @AuthenticationPrincipal Usuario usuarioLogado) {

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        Produto novoProduto = request.toModel(categoria, usuarioLogado);
        produtoRepository.save(novoProduto);
        return novoProduto.toString();
    }
}
