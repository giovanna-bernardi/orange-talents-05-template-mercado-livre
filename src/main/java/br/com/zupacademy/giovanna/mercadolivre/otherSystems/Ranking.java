package br.com.zupacademy.giovanna.mercadolivre.otherSystems;

import br.com.zupacademy.giovanna.mercadolivre.shopping.Compra;
import br.com.zupacademy.giovanna.mercadolivre.shopping.evento.EventoCompraSucesso;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class Ranking implements EventoCompraSucesso {
    @Override
    public void processa(Compra compra) {
        Assert.isTrue(compra.processadaComSucesso(), "Compra não concluída com sucesso " + compra);
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request =
                Map.of("compraId", compra.getId(), "vendedorId", compra.getVendedorId());
        restTemplate.postForEntity(
                "http://localhost:8080/ranking", request, String.class);
    }
}
