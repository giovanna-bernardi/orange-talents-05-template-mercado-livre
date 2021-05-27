package br.com.zupacademy.giovanna.mercadolivre.security;

import br.com.zupacademy.giovanna.mercadolivre.security.token.Token;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;
import br.com.zupacademy.giovanna.mercadolivre.user.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Token token;
    private UsuarioRepository usuarioRepository;


    public JwtAuthenticationFilter(Token token, UsuarioRepository usuarioRepository) {
        this.token = token;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String tokenDaRequest = pegaTokenDaRequest(httpServletRequest);
        boolean tokenValido = token.isTokenValid(tokenDaRequest);

        if(tokenValido) {
            autenticaUsuario(tokenDaRequest);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String pegaTokenDaRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        int prefixo = "Bearer ".length();
        return token.substring(prefixo, token.length());
    }

    private void autenticaUsuario(String tokenDaRequest){
        Long idDoUsuarioDoToken = token.getIdDoUsuarioDoToken(tokenDaRequest);
        Optional<Usuario> usuarioDoToken = Optional.ofNullable(usuarioRepository.findById(idDoUsuarioDoToken)).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        UsernamePasswordAuthenticationToken authetication = new
                UsernamePasswordAuthenticationToken(usuarioDoToken.get(), null,
                usuarioDoToken.get().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authetication);
    }


}
