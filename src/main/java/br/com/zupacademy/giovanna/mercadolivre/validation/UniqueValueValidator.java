package br.com.zupacademy.giovanna.mercadolivre.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private String domainField;
    private Class<?> domainClass;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        domainField = constraintAnnotation.fieldName();
        domainClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = entityManager.createQuery(
                "select 1 from " + domainClass.getName() + " where " + domainField + " = :fieldName");
        query.setParameter("fieldName", value);

        List<?> resultado = query.getResultList();

        Assert.state(resultado.size() <= 1, "Existe mais de um registro com " + domainField + " = " + value + " no banco!");

        return resultado.isEmpty();
    }
}
