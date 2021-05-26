package br.com.zupacademy.giovanna.mercadolivre.categoria.dto;

import br.com.zupacademy.giovanna.mercadolivre.categoria.Categoria;
import br.com.zupacademy.giovanna.mercadolivre.categoria.CategoriaRepository;
import br.com.zupacademy.giovanna.mercadolivre.validation.ExistsId;
import br.com.zupacademy.giovanna.mercadolivre.validation.UniqueValue;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

public class CategoriaRequest {

    @NotBlank(message = "O nome não pode estar vazio nem ser nulo")
    @UniqueValue(domainClass = Categoria.class, fieldName = "nome")
    private String nome;

    @ExistsId(domainClass = Categoria.class, fieldName = "id")
    private Long categoriaMaeId;

    public CategoriaRequest(@NotBlank String nome) {
        this.nome = nome;
    }

    public CategoriaRequest(@NotBlank String nome, Long categoriaMaeId) {
        this.nome = nome;
        this.categoriaMaeId = categoriaMaeId;
    }

    public Categoria toModel(CategoriaRepository repository) {
        if(this.categoriaMaeId != null) {
            Categoria categoriaMae = repository
                    .findById(this.categoriaMaeId)
                    .orElseThrow(() -> new IllegalArgumentException("Categoria mãe não encontrada"));
            return new Categoria(this.nome, categoriaMae);
        }
        return new Categoria(this.nome);
    }

}
