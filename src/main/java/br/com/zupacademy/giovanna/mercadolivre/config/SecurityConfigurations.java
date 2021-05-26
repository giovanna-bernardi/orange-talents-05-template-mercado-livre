package br.com.zupacademy.giovanna.mercadolivre.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    /* Por enquanto vou deixar só assim para ficar mais fácil de testar sem ter que criar profiles */

    /* Em vez de criar este arquivo de configurações permitindo tudo,
    * poderia ter colocado na classe de application:
    * @SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) */

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .and().csrf().disable();
    }
}
