package br.com.zupacademy.giovanna.mercadolivre.categoria;

import br.com.zupacademy.giovanna.mercadolivre.categoria.dto.CategoriaRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class NovaCategoriaController {

    private CategoriaRepository categoriaRepository;

    public NovaCategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @PostMapping(value = "/categorias")
    @Transactional
    public ResponseEntity<String> cadastra(@RequestBody @Valid CategoriaRequest request) {
        Categoria categoria = request.toModel(categoriaRepository);
        categoriaRepository.save(categoria);
        return ResponseEntity.ok(categoria.toString());
    }
}
