package com.baz.divisas.general.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bson.types.ObjectId;


public class ValidaObjectIdAspect implements ConstraintValidator<ValidaObjectId, String> {

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {

        return ObjectId.isValid(id);
    }

}
