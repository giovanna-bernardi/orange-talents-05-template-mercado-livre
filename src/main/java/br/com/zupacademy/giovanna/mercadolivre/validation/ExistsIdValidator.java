package br.com.zupacademy.giovanna.mercadolivre.validation;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistsIdValidator implements ConstraintValidator<ExistsId, Object> {

    private String domainField;
    private Class<?> domainClass;

    private final EntityManager entityManager;

    public ExistsIdValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(ExistsId constraintAnnotation) {
        domainField = constraintAnnotation.fieldName();
        domainClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null) return true; // para campos não obrigatórios

        Query query = entityManager.createQuery(
                "select 1 from " + domainClass.getName() + " where " + domainField + " = :fieldValue");
        query.setParameter("fieldValue", value);
        List<?> listaDeResultados = query.getResultList();

        return !listaDeResultados.isEmpty();
    }
}
