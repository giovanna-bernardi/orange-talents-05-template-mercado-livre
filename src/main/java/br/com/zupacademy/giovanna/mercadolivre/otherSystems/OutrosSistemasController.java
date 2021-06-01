package br.com.zupacademy.giovanna.mercadolivre.otherSystems;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OutrosSistemasController {

    @PostMapping("/notas-fiscais")
    public void criaNota(@RequestBody @Valid CompraNFRequest request) throws InterruptedException {
        System.out.println("Criando uma nota fiscal para " + request);
        Thread.sleep(150);
    }

    @PostMapping("/ranking")
    public void ranking(@RequestBody @Valid CompraRankingRequest request) throws InterruptedException {
        System.out.println("Adicionando ao ranking " + request);
        Thread.sleep(150);
    }
}
