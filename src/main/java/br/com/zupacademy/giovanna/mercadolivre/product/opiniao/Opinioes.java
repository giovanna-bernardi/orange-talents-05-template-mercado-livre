package br.com.zupacademy.giovanna.mercadolivre.product.opiniao;

import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Isola as operações sobre um conjunto de opiniões
 */
public class Opinioes {

    private Set<Opiniao> opinioes;

    public Opinioes(Set<Opiniao> opinioes) {
        this.opinioes = opinioes;
    }

    public <T> Set<T> mapOpinioes(Function<Opiniao, T> funcaoMapeadora) {
        return this.opinioes.stream().map(funcaoMapeadora).collect(Collectors.toSet());
    }

    public double mediaDeNotas() {
        Set<Integer> notas = mapOpinioes(opiniao -> opiniao.getNota());
        OptionalDouble average = notas.stream().mapToInt(nota -> nota).average();
        return average.orElse( 0.0);
    }

    public int totalDeNotas() {
        return opinioes.size();
    }
}
