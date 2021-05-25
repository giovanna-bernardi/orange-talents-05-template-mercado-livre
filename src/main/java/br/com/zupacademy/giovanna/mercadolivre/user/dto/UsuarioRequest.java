package br.com.zupacademy.giovanna.mercadolivre.user.dto;

import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;
import br.com.zupacademy.giovanna.mercadolivre.validation.UniqueValue;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UsuarioRequest {

    @NotBlank(message = "O login não pode estar em branco e nem ser nulo")
    @Email(message = "O login deve ter um formato válido de e-mail")
    @UniqueValue(domainClass = Usuario.class, fieldName = "login")
    private String login;

    @NotBlank(message = "A senha não pode estar em branco e nem ser nula")
    @Length(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;

    public UsuarioRequest(@NotBlank @Email String login, @NotBlank @Length(min = 6) String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Usuario toModel() {
        Usuario usuario = new Usuario(this.login, this.senha);
        return usuario;
    }

}
