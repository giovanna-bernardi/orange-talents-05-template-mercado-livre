package br.com.zupacademy.giovanna.mercadolivre.validation;

import br.com.zupacademy.giovanna.mercadolivre.product.produto.dto.ProdutoRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NomeNaoPodeSerIgualValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ProdutoRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(errors.hasErrors()) return;

        ProdutoRequest request = (ProdutoRequest) o;
        if(request.caracteristicaTemMesmoNome()) {
            errors.rejectValue("caracteristicas",
                    null,
                    "Características de mesmo nome não são permitidas");
        }
    }
}
