package br.com.zupacademy.giovanna.mercadolivre.product.pergunta.dto;

import br.com.zupacademy.giovanna.mercadolivre.user.Usuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PerguntaResponse {
    private String titulo;
    private LocalDateTime instante;
    private String donoDaPergunta;

    public PerguntaResponse(String titulo, LocalDateTime instante, Usuario donoDaPergunta) {
        this.titulo = titulo;
        this.instante = instante;
        this.donoDaPergunta = donoDaPergunta.getUsername();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getInstante() {
        return instante.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public String getDonoDaPergunta() {
        return donoDaPergunta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PerguntaResponse that = (PerguntaResponse) o;

        if (!titulo.equals(that.titulo)) return false;
        return donoDaPergunta.equals(that.donoDaPergunta);
    }

    @Override
    public int hashCode() {
        int result = titulo.hashCode();
        result = 31 * result + donoDaPergunta.hashCode();
        return result;
    }
}
