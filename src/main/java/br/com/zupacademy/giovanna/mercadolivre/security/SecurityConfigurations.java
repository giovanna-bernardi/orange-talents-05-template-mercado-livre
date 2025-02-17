package br.com.zupacademy.giovanna.mercadolivre.security;

import br.com.zupacademy.giovanna.mercadolivre.security.token.Token;
import br.com.zupacademy.giovanna.mercadolivre.user.BuscaUsuarioPorUsername;
import br.com.zupacademy.giovanna.mercadolivre.user.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private BuscaUsuarioPorUsername buscaUsuarioPorUsername;
    @Autowired
    private Token token;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    // Authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(buscaUsuarioPorUsername)
                .passwordEncoder(passwordEncoder);
    }

    // Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST,"/usuarios").permitAll()
                .antMatchers(HttpMethod.GET,"/produtos/*").permitAll()
                .antMatchers(HttpMethod.POST,"/notas-fiscais").permitAll()
                .antMatchers(HttpMethod.POST,"/ranking").permitAll()
                .antMatchers(HttpMethod.POST,"/categorias").authenticated() // só deixando explícito, pois no anyRequest ele já está para authenticated()
                .antMatchers(HttpMethod.POST,"/produtos").authenticated()
                .antMatchers(HttpMethod.POST,"/compras").authenticated()
                .anyRequest().authenticated()
                .and().cors()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(
                        new JwtAuthenticationFilter(token, usuarioRepository),
                UsernamePasswordAuthenticationFilter.class);
    }
}
