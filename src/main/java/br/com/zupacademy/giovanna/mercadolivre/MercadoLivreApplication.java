package br.com.zupacademy.giovanna.mercadolivre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/* Em vez de criar este arquivo de configurações permitindo tudo,
 * poderia ter colocado na classe de application:
 * @SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) */

@SpringBootApplication
@EnableSpringDataWebSupport
public class MercadoLivreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercadoLivreApplication.class, args);
	}

}
