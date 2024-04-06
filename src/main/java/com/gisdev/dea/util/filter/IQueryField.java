package com.gisdev.dea.util.filter;

import com.gisdev.dea.entity.AbstractEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;

public interface IQueryField {
    /**
     * @return name of field to filter
     */
    String getFieldName();

    /**
     *
     * @return name of attribute in the entity
     */
    String getAttributeName();

    /**
     *
     * @return full path of attribute to sort
     */
    String getSortName();

    /**
     * @param root
     * @param criteriaBuilder
     * @return the entity field as Expression and its type
     */
   <T extends AbstractEntity> FilterField getExpressionField(Root<T> root, CriteriaBuilder criteriaBuilder);
}
