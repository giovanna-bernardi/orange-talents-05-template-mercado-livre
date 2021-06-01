package br.com.zupacademy.giovanna.mercadolivre.shopping;

import br.com.zupacademy.giovanna.mercadolivre.product.produto.Produto;
import br.com.zupacademy.giovanna.mercadolivre.shopping.dto.RetornoGatewayPagamento;
import br.com.zupacademy.giovanna.mercadolivre.shopping.transacao.Transacao;
import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;
import io.jsonwebtoken.lang.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
    private Set<Transacao> transacoes = new HashSet<>();

    @Deprecated
    public Compra() {
    }

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
                ", transacoes=" + transacoes +
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

    public Long getVendedorId() {return this.produto.getVendedorId();}

    public String getEmailDoVendedorDoProduto() {
        return this.produto.getEmailDoVendedor();
    }

    public String urlRedirecionamento(UriComponentsBuilder uriComponentsBuilder) {
        return this.gateway.criaUrlRetorno(this, uriComponentsBuilder);
    }

    private Set<Transacao> transacoesConcluidasComSucesso() {
        Set<Transacao> transacoesConcluidasComSucesso = this.transacoes.stream()
                .filter(Transacao::concluidaComSucesso)
                .collect(Collectors.toSet());

        // A compra tem que ter no máximo uma transação concluída com sucesso
        Assert.isTrue(transacoesConcluidasComSucesso.size() <= 1,
                "Tem mais de uma transação concluída com sucesso na compra de id = " + this.id);

        return transacoesConcluidasComSucesso;
    }

    public void adicionaTransacao(@Valid RetornoGatewayPagamento request) {
        Transacao novaTransacao = request.toTransacao(this);

        // Não pode ter mais de uma transação com mesmo id vindo do gateway
        // Não é para processar uma mesma transação mais de uma vez
        Assert.isTrue(!this.transacoes.contains(novaTransacao),
                "Uma transação igual a essa já foi processada. TransacaoId: " + novaTransacao.getGatewayTransacaoId());

        // Para poder adicionar uma nova transação, a compra não pode ter nenhuma transação concluída com sucesso anteriormente.
        Assert.isTrue(transacoesConcluidasComSucesso().isEmpty(),
                "Essa compra já foi concluída com sucesso");

        this.transacoes.add(request.toTransacao(this));
    }

    public boolean processadaComSucesso() {
        return !transacoesConcluidasComSucesso().isEmpty();
    }
}
