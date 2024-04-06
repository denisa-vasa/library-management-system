package com.gisdev.dea.util.filter;


public enum FilterOperator {
    EQ("eq"),
    NOT_EQ("neq"),
    GT("gt"),
    LT("lt"),
    GTE("gte"),
    LTE("lte"),
    LIKE("like"),
    ILIKE("ilike"),
    START_WITH("start_with"),
    IN("in"),
    NOT_IN("not_in"),
    IS_NOT_NULL("is_not_null"),
    IS_NULL("is_null");

    private final String operator;

    FilterOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return String.format(":%s:", operator);
    }

    @Override
    public String toString() {
        return operator;
    }
}
