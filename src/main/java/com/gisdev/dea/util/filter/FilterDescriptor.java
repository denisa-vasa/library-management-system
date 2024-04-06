package com.gisdev.dea.util.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilterDescriptor<T extends IQueryField, V> {

    private T field;
    private String fieldAlias;
    private FilterOperator operator;
    private V value;

    public FilterDescriptor(T field, FilterOperator operator, V value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }
}