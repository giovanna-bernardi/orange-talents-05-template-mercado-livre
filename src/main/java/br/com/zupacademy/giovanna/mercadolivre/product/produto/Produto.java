package br.com.zupacademy.giovanna.mercadolivre.product.produto;

import br.com.zupacademy.giovanna.mercadolivre.imagem.Imagem;
import br.com.zupacademy.giovanna.mercadolivre.product.caracteristica.Caracteristica;
import br.com.zupacademy.giovanna.mercadolivre.product.caracteristica.dto.CaracteristicaRequest;
import br.com.zupacademy.giovanna.mercadolivre.product.categoria.Categoria;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;
import br.com.zupacademy.giovanna.mercadolivre.validation.ExistsId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    private Set<Imagem> imagens = new HashSet<>();


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

    public boolean pertenceAo(Usuario usuario) {
        return this.vendedor.equals(usuario);
    }

    public void adicionaImagens(Set<String> urls){
        Set<Imagem> novasImagens = urls.stream()
                .map(url -> new Imagem(url, this))
                .collect(Collectors.toSet());
        this.imagens.addAll(novasImagens);
    }
}
