package br.com.zupacademy.giovanna.mercadolivre.shopping.transacao;

import br.com.zupacademy.giovanna.mercadolivre.shopping.Compra;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String gatewayTransacaoId;
    @NotNull @Enumerated(EnumType.STRING)
    private StatusTransacao status;
    private LocalDateTime instante;
    @ManyToOne
    private Compra compra;

    @Deprecated
    public Transacao() {
    }

    public Transacao(@NotNull StatusTransacao status,
                     @NotBlank String gatewayTransacaoId,
                     @NotNull @Valid Compra compra) {
        this.gatewayTransacaoId = gatewayTransacaoId;
        this.status = status;
        this.instante = LocalDateTime.now();
        this.compra = compra;
    }

    public boolean concluidaComSucesso() {
        return this.status.equals(StatusTransacao.SUCESSO);
    }

    public String getGatewayTransacaoId() {
        return gatewayTransacaoId;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "id=" + id +
                ", gatewayTransacaoId='" + gatewayTransacaoId + '\'' +
                ", status=" + status +
                ", instante=" + instante +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transacao transacao = (Transacao) o;

        return gatewayTransacaoId.equals(transacao.gatewayTransacaoId);
    }

    @Override
    public int hashCode() {
        return gatewayTransacaoId.hashCode();
    }
}
