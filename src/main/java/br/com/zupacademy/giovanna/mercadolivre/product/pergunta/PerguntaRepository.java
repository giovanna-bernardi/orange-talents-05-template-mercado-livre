package br.com.zupacademy.giovanna.mercadolivre.product.pergunta;

import br.com.zupacademy.giovanna.mercadolivre.product.pergunta.dto.PerguntaResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Long>, JpaSpecificationExecutor<Pergunta> {

    Set<PerguntaResponse> findTop5ByProdutoIdOrderByInstanteDesc(Long produtoId);
}
