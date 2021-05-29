package br.com.zupacademy.giovanna.mercadolivre.product.produto.dto;

import br.com.zupacademy.giovanna.mercadolivre.product.caracteristica.dto.CaracteristicaResponse;
import br.com.zupacademy.giovanna.mercadolivre.product.opiniao.Opinioes;
import br.com.zupacademy.giovanna.mercadolivre.product.pergunta.PerguntaRepository;
import br.com.zupacademy.giovanna.mercadolivre.product.pergunta.dto.PerguntaResponse;
import br.com.zupacademy.giovanna.mercadolivre.product.produto.Produto;
import java.math.BigDecimal;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;

public class ProdutoDetalheResponse {

    // informações básicas
    private String nome;
    private BigDecimal preco;
    private int quantidadeDisponivel;
    private String descricao;

    // informações trabalhadas
    private Set<CaracteristicaResponse> caracteristicas;
    private Set<String> linksParaImagens;
    private Set<PerguntaResponse> perguntas;
    private Set<Map<String, String>> opinioes;
    private double mediaNotasOpinioes;
    private int totalDeNotas;


    public ProdutoDetalheResponse(Produto produto, PerguntaRepository perguntaRepository) {
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.quantidadeDisponivel = produto.getQuantidadeDisponivel();

//        this.caracteristicas = produto
//                .mapCaracteristicas(caracteristica -> new CaracteristicaResponse(caracteristica));

        // mesma coisa da de cima mas usando method reference
        this.caracteristicas = produto.mapCaracteristicas(CaracteristicaResponse::new);
        this.linksParaImagens = produto.mapImagens(imagem -> imagem.getCaminhoImagem());

        // para não trazer todas de uma vez, aí para um link "ver todas",
        // fazer um GetMapping só pra puxar todas (de preferência com um Pageable)
        this.perguntas = perguntaRepository.findTop5ByProdutoIdOrderByInstanteDesc(produto.getId());

        Opinioes opinioes = produto.getOpinioes();

        this.opinioes = opinioes.mapOpinioes(opiniao -> {
            return Map.of("titulo", opiniao.getTitulo(), "descricao", opiniao.getDescricao());
        });

        this.mediaNotasOpinioes = opinioes.mediaDeNotas();
        this.totalDeNotas = opinioes.totalDeNotas();

    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public String getDescricao() {
        return descricao;
    }

    public Set<CaracteristicaResponse> getCaracteristicas() {
        return caracteristicas;
    }

    public Set<String> getLinksParaImagens() {
        return linksParaImagens;
    }

    public Set<PerguntaResponse> getPerguntas() {
        return perguntas;
    }

    public Set<Map<String, String>> getOpinioes() {
        return opinioes;
    }

    public double getMediaNotasOpinioes() {
        return mediaNotasOpinioes;
    }

    public int getTotalDeNotas() {
        return totalDeNotas;
    }
}
