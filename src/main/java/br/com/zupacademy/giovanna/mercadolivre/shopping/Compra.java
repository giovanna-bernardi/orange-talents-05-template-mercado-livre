package br.com.zupacademy.giovanna.mercadolivre.shopping;

import br.com.zupacademy.giovanna.mercadolivre.product.produto.Produto;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.net.URI;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    @Column(nullable = false)
    private int quantidade;

    @ManyToOne
    @NotNull @Valid
    private Produto produto;

    @ManyToOne
    @NotNull @Valid
    private Usuario comprador;

    @Enumerated(EnumType.STRING) @NotNull
    private GatewayPagamento gateway;

    @Enumerated(EnumType.STRING) @NotNull
    private StatusCompra status = StatusCompra.INICIADA;

    @NotNull @Positive
    @Column(nullable = false)
    private BigDecimal precoNoMomentoDaCompra;

    public Compra(@Positive int quantidade,
                  @NotNull @Valid Produto produto,
                  @NotNull @Valid Usuario comprador,
                  @NotNull GatewayPagamento gateway) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.comprador = comprador;
        this.gateway = gateway;
        this.precoNoMomentoDaCompra = produto.getPreco();
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                ", produto=" + produto +
                ", comprador=" + comprador +
                ", gateway=" + gateway +
                ", status=" + status +
                ", precoNoMomentoDaCompra=" + precoNoMomentoDaCompra +
                '}';
    }

    public Long getId() {
        return id;
    }
    
    public Long getCompradorId() {
        return this.comprador.getId();
    }

    public String getEmailDoComprador() {
        return this.comprador.getUsername();
    }

    public String getEmailDoVendedorDoProduto() {
        return this.produto.getEmailDoVendedor();
    }

    public String urlRedirecionamento(UriComponentsBuilder uriComponentsBuilder) {
        return this.gateway.criaUrlRetorno(this, uriComponentsBuilder);
    }
}
