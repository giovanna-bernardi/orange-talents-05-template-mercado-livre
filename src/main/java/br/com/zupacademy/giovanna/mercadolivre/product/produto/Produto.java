package br.com.zupacademy.giovanna.mercadolivre.product.produto;

import br.com.zupacademy.giovanna.mercadolivre.product.imagem.ImagemProduto;
import br.com.zupacademy.giovanna.mercadolivre.product.caracteristica.Caracteristica;
import br.com.zupacademy.giovanna.mercadolivre.product.caracteristica.dto.CaracteristicaRequest;
import br.com.zupacademy.giovanna.mercadolivre.product.categoria.Categoria;
import br.com.zupacademy.giovanna.mercadolivre.product.opiniao.Opiniao;
import br.com.zupacademy.giovanna.mercadolivre.product.opiniao.Opinioes;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotNull @Positive
    @Column(nullable = false)
    private BigDecimal preco;

    @NotNull @Positive
    @Column(nullable = false)
    private int quantidadeDisponivel;

    @NotBlank @Length(max = 1000)
    @Column(nullable = false)
    private String descricao;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime instanteCadastro = LocalDateTime.now();

    @NotNull
    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Usuario vendedor;

    // cascade = CascadeType.PERSIST
    // sempre que cadastrar um produto, quero cadastrar suas características também
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Set<Caracteristica> caracteristicas = new HashSet<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagemProduto> imagens = new HashSet<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<Opiniao> opinioes = new HashSet<>();

    @Deprecated
    public Produto() {
    }

    public Produto(@NotBlank String nome,
                   @NotNull @Positive BigDecimal preco,
                   @NotNull @Positive int quantidadeDisponivel,
                   @NotBlank @Length(max = 1000) String descricao,
                   @NotNull @Valid Categoria categoria,
                   @NotNull @Valid Usuario vendedor,
                   @Size(min = 3) @Valid Collection<CaracteristicaRequest> caracteristicas) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.descricao = descricao;
        this.categoria = categoria;
        this.vendedor = vendedor;
        this.caracteristicas.addAll(caracteristicas.stream()
                .map(c -> c.toModel(this))
                .collect(Collectors.toSet()));
    }



    public boolean pertenceAo(Usuario usuario) {
        return this.vendedor.equals(usuario);
    }

    public void adicionaImagens(Set<String> urls){
        Set<ImagemProduto> novasImagens = urls.stream()
                .map(url -> new ImagemProduto(url, this))
                .collect(Collectors.toSet());
        this.imagens.addAll(novasImagens);
    }

    // Abaixo tem uma versão generalizada desse método!
//    public Set<CaracteristicaResponse> mapCaracteristicas(Function<Caracteristica, CaracteristicaResponse> funcaoMapeadora){
//        return this.caracteristicas.stream().map(funcaoMapeadora).collect(Collectors.toSet());
//    }

    // Assim, posso mapear uma característica para qualquer outro DTO, com qualquer tipo de função mapeadora
    // Não expõe as características de produto (para não correr o risco de serem usadas de maneira indevida
    public <T> Set<T> mapCaracteristicas(Function<Caracteristica, T> funcaoMapeadora){
        return this.caracteristicas.stream().map(funcaoMapeadora).collect(Collectors.toSet());
    }

    public <T> Set<T> mapImagens(Function<ImagemProduto, T> funcaoMapeadora) {
        return this.imagens.stream().map(funcaoMapeadora).collect(Collectors.toSet());
    }

    // Vai ficar em Opinioes agora
//    public <T> Set<T> mapOpinioes(Function<Opiniao, T> funcaoMapeadora) {
//        return this.opinioes.stream().map(funcaoMapeadora).collect(Collectors.toSet());
//    }

    public Opinioes getOpinioes() {
        return new Opinioes(this.opinioes);
    }

    public String getEmailDoVendedor() {
        return this.vendedor.getUsername();
    }


    public Long getId() {
        return id;
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

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidadeDisponivel=" + quantidadeDisponivel +
                ", descricao='" + descricao + '\'' +
                ", instanteCadastro=" + instanteCadastro.format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                ", categoria=" + categoria +
                ", vendedor=" + vendedor.getUsername() +
                ", caracteristicas=" + caracteristicas +
                ", imagens=" + imagens +
                '}';
    }

    public boolean abateEstoque(@Positive int quantidadeEscolhida) {
        Assert.isTrue(quantidadeEscolhida > 0, "A quantidade deve ser maior que zero. Quantidade passada " + quantidadeEscolhida);

        if(this.quantidadeDisponivel >= quantidadeEscolhida) {
            this.quantidadeDisponivel -= quantidadeEscolhida;
            return true;
        }

        return false;
    }
}
