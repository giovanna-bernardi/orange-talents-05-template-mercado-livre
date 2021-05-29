package br.com.zupacademy.giovanna.mercadolivre.product.pergunta;

import br.com.zupacademy.giovanna.mercadolivre.product.produto.Produto;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private LocalDateTime instante = LocalDateTime.now();

    @NotNull
    @ManyToOne
    private Produto produto;

    @NotNull
    @ManyToOne
    private Usuario donoDaPergunta;

    @Deprecated
    public Pergunta() {
    }

    public Pergunta(@NotBlank String titulo,
                    @NotNull Produto produto,
                    @NotNull Usuario donoDaPergunta) {
        this.titulo = titulo;
        this.produto = produto;
        this.donoDaPergunta = donoDaPergunta;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public Produto getProduto() {
        return produto;
    }

    public String getEmailDoVendedorDoProduto() {
        return this.produto.getEmailDoVendedor();
    }

    public String getEmailDoDonoDaPergunta() {
        return this.donoDaPergunta.getUsername();
    }

    @Override
    public String toString() {
        return "Pergunta{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", instante=" + instante +
                ", produto=" + produto +
                ", donoDaPergunta=" + donoDaPergunta.getUsername() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pergunta pergunta = (Pergunta) o;

        if (!titulo.equals(pergunta.titulo)) return false;
        return donoDaPergunta.equals(pergunta.donoDaPergunta);
    }

    @Override
    public int hashCode() {
        int result = titulo.hashCode();
        result = 31 * result + donoDaPergunta.hashCode();
        return result;
    }
}
