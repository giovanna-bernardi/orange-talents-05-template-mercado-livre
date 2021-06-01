package br.com.zupacademy.giovanna.mercadolivre.otherSystems;

public class CompraNFRequest {
    private Long compraId;
    private Long compradorId;

    public CompraNFRequest(Long compraId, Long compradorId) {
        this.compraId = compraId;
        this.compradorId = compradorId;
    }

    @Override
    public String toString() {
        return "CompraNFRequest{" +
                "compraId=" + compraId +
                ", compradorId=" + compradorId +
                '}';
    }
}
