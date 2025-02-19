package br.com.zupacademy.giovanna.mercadolivre.user;

import br.com.zupacademy.giovanna.mercadolivre.user.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class BuscaUsuarioPorUsername implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public BuscaUsuarioPorUsername(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
