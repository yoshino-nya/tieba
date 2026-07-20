package org.example.tieba.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AtLeastOneNotBlankValidator implements ConstraintValidator<AtLeastOneNotBlank, Object> {

    private String[] specifiedFields;

    @Override
    public void initialize(AtLeastOneNotBlank constraintAnnotation) {
        this.specifiedFields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context) {
        if (dto == null)
            return true;
        List<Field> fieldsToCheck = new ArrayList<>();
        if (specifiedFields.length == 0) {
            Collections.addAll(fieldsToCheck, dto.getClass().getDeclaredFields());
        } else {
            for (String filedName : specifiedFields) {
                try {
                    Field field = dto.getClass().getDeclaredField(filedName);
                    fieldsToCheck.add(field);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for (Field field : fieldsToCheck) {
            field.setAccessible(true);
            try {
                if (isNotBlank(field.get(dto))) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                // 理论上不会发生，因为 setAccessible(true)
                throw new RuntimeException("Failed to access field: " + field.getName(), e);
            }
        }
        return false;
    }

    private boolean isNotBlank(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof String str) {
            return !str.isBlank();
        }
        return true;
    }
}
