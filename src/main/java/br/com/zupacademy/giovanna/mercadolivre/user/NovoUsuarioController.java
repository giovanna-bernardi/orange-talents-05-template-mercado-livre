package br.com.zupacademy.giovanna.mercadolivre.user;

import br.com.zupacademy.giovanna.mercadolivre.user.dto.UsuarioRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class NovoUsuarioController {

    private UsuarioRepository usuarioRepository;

    public NovoUsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastra(@RequestBody @Valid UsuarioRequest request) {
        Usuario usuario = request.toModel();
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }
}
