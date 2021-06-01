package br.com.zupacademy.giovanna.mercadolivre.otherSystems;

public class CompraRankingRequest {
    private Long compraId;
    private Long vendedorId;

    public CompraRankingRequest(Long compraId, Long vendedorId) {
        this.compraId = compraId;
        this.vendedorId = vendedorId;
    }

    @Override
    public String toString() {
        return "CompraRankingRequest{" +
                "compraId=" + compraId +
                ", vendedorId=" + vendedorId +
                '}';
    }
}
