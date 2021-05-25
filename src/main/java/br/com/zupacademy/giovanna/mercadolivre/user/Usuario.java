package br.com.zupacademy.giovanna.mercadolivre.user;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O login não pode estar em branco e nem ser nulo")
    @Email(message = "O login deve ter um formato válido de e-mail")
    @Column(nullable = false)
    private String login;

    @NotBlank(message = "A senha não pode estar em branco e nem ser nula")
    @Length(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @Column(nullable = false)
    private String senha;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime instanteCadastro = LocalDateTime.now();

    @Deprecated
    public Usuario() {
    }

    /**
     *
     * @param login string no formato de e-mail
     * @param senhaSemCriptografia string em texto limpo
     */
    public Usuario(@NotBlank @Email String login,
                   @NotBlank @Length(min = 6) String senhaSemCriptografia) {
        Assert.isTrue(StringUtils.hasLength(login), "O login não pode estar em branco");
        Assert.isTrue(senhaSemCriptografia.length() >= 6, "A senha deve ter no mínimo 6 caracteres");

        this.login = login;
        this.senha = criptografaSenha(senhaSemCriptografia);
    }

    public String criptografaSenha(String senhaSemCriptografia){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder(); // usa BCrypt por padrão
        return passwordEncoder.encode(senhaSemCriptografia);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
