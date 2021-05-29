package br.com.zupacademy.giovanna.mercadolivre.product.opiniao;

import br.com.zupacademy.giovanna.mercadolivre.product.produto.Produto;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Opiniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Min(1) @Max(5)
    @Column(nullable = false)
    private int nota;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @NotBlank @Size(max = 500)
    @Column(nullable = false)
    private String descricao;

    @NotNull
    @ManyToOne
    private Produto produto;

    @NotNull
    @ManyToOne
    private Usuario consumidor;

    @Deprecated
    public Opiniao() {
    }

    public Opiniao(int nota, String titulo, String descricao, Produto produto, Usuario consumidor) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.produto = produto;
        this.consumidor = consumidor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getNota() {
        return nota;
    }

    @Override
    public String toString() {
        return "Opiniao{" +
                "id=" + id +
                ", nota=" + nota +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", produto=" + produto +
                ", consumidor=" + consumidor.getUsername() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Opiniao opiniao = (Opiniao) o;

        if (nota != opiniao.nota) return false;
        if (!titulo.equals(opiniao.titulo)) return false;
        if (!descricao.equals(opiniao.descricao)) return false;
        if (!produto.equals(opiniao.produto)) return false;
        return consumidor.equals(opiniao.consumidor);
    }

    @Override
    public int hashCode() {
        int result = nota;
        result = 31 * result + titulo.hashCode();
        result = 31 * result + descricao.hashCode();
        result = 31 * result + produto.hashCode();
        result = 31 * result + consumidor.hashCode();
        return result;
    }
}
