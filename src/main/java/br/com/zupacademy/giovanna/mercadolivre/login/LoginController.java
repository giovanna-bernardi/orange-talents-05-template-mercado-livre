package br.com.zupacademy.giovanna.mercadolivre.login;

import br.com.zupacademy.giovanna.mercadolivre.seguranca.token.Token;
import br.com.zupacademy.giovanna.mercadolivre.seguranca.token.TokenResponse;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final Token tokenComponent;

    public LoginController(AuthenticationManager authenticationManager, Token tokenComponent) {
        this.authenticationManager = authenticationManager;
        this.tokenComponent = tokenComponent;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenResponse> autentica(@RequestBody @Valid LoginRequest request) {
        UsernamePasswordAuthenticationToken login = request.converte();

        try{
            Authentication authentication = authenticationManager.authenticate(login);
            String token = tokenComponent.geraToken(authentication);
            return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
        } catch(AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
