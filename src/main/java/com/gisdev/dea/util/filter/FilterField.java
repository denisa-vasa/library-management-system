package com.gisdev.dea.util.filter;

import jakarta.persistence.criteria.Expression;

public class FilterField {

    private final Expression<?> expression;
    private final Class<?> classType;

    private FilterField(Expression<?> expression, Class<?> classType) {
        this.expression = expression;
        this.classType = classType;
    }

    public static FilterField of(Expression<?> expression, Class<?> classType) {
        return new FilterField(expression, classType);
    }

    public Expression<?> getExpression() {
        return expression;
    }

    public Class<?> getClassType() {
        return classType;
    }
}
