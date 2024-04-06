package com.gisdev.dea.util.filter;

import com.gisdev.dea.entity.AbstractEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gisdev.dea.util.constant.RestConstants.IN_SPLITERATOR;

/**
 * Generic class for building specification
 *
 * @param <T> is an Entity class
 */
@Slf4j
@Component
public class SpecificationBuilder<T extends AbstractEntity> {

    public <E extends IQueryField> Specification<T> buildSpecification(List<FilterDescriptor<E, ?>> filterDescriptors) {

        return ((root, query, criteriaBuilder) -> {

            List<Predicate> predicateList = new ArrayList<>();
            for (FilterDescriptor<E, ?> filterDescriptor : filterDescriptors) {
                Predicate result = buildCondition(filterDescriptor, root, criteriaBuilder);
                if (result != null) {
                    predicateList.add(result);
                }
            }
            return criteriaBuilder.and(predicateList.toArray(Predicate[]::new));
        });
    }

    /**
     * @param descriptor      contains filter field, operator and value
     * @param root            is the entity class
     * @param criteriaBuilder used to create where clauses
     * @return predicate condition
     */
    @SuppressWarnings("unchecked")
    private Predicate buildCondition(FilterDescriptor<?, ?> descriptor, Root<T> root, CriteriaBuilder criteriaBuilder) {

        FilterField filterField = getExpressionField(descriptor.getField(), root, criteriaBuilder);
        Expression<?> field = filterField.getExpression();

        switch (descriptor.getOperator()) {

            case EQ:
                if (isFieldType(filterField, String.class)) {
                    String value = getStringValueFrom(descriptor);
                    return criteriaBuilder.equal(field, value);
                } else if (isFieldType(filterField, Long.class)) {
                    Long value = getLongValueFrom(descriptor);
                    return criteriaBuilder.equal(field, value);
                } else if (isFieldType(filterField, Integer.class)) {
                    Integer value = getIntegerValueFrom(descriptor);
                    return criteriaBuilder.equal(field, value);
                } else if (isFieldType(filterField, LocalDate.class)) {
                    LocalDate value = getLocalDateValueFrom(descriptor);
                    return criteriaBuilder.equal(field, value);
                } else if (isFieldType(filterField, Boolean.class)) {
                    Boolean value = getBooleanValueFrom(descriptor);
                    return criteriaBuilder.equal(field, value);
                }

            case GT:
                if (isFieldType(filterField, Long.class)) {
                    Long value = getLongValueFrom(descriptor);
                    return criteriaBuilder.greaterThan((Expression<Long>) field, value);
                } else if (isFieldType(filterField, Integer.class)) {
                    Integer value = getIntegerValueFrom(descriptor);
                    return criteriaBuilder.greaterThan((Expression<Integer>) field, value);
                } else if (isFieldType(filterField, LocalDate.class)) {
                    LocalDate value = getLocalDateValueFrom(descriptor);
                    return criteriaBuilder.greaterThan((Expression<LocalDate>) field, value);
                } else if (isFieldType(filterField, LocalDateTime.class)) {
                    LocalDateTime value = getLocalDateTimeValueFrom(descriptor);
                    return criteriaBuilder.greaterThan((Expression<LocalDateTime>) field, value);
                } else if (isFieldType(filterField, Double.class)) {
                    Double value = getDoubleValueFrom(descriptor);
                    return criteriaBuilder.greaterThan((Expression<Double>) field, value);
                }

            case GTE:
                if (isFieldType(filterField, Long.class)) {
                    Long value = getLongValueFrom(descriptor);
                    return criteriaBuilder.greaterThanOrEqualTo((Expression<Long>) field, value);
                } else if (isFieldType(filterField, Integer.class)) {
                    Integer value = getIntegerValueFrom(descriptor);
                    return criteriaBuilder.greaterThanOrEqualTo((Expression<Integer>) field, value);
                } else if (isFieldType(filterField, LocalDate.class)) {
                    LocalDate value = getLocalDateValueFrom(descriptor);
                    return criteriaBuilder.greaterThanOrEqualTo((Expression<LocalDate>) field, value);
                } else if (isFieldType(filterField, LocalDateTime.class)) {
                    LocalDateTime value = getLocalDateTimeValueFrom(descriptor);
                    return criteriaBuilder.greaterThanOrEqualTo((Expression<LocalDateTime>) field, value);
                }

            case LIKE:
                if (isFieldType(filterField, String.class)) {
                    String value = getStringValueFrom(descriptor);
                    return criteriaBuilder.like((Expression<String>) field, "%" + value + "%");
                }

            case ILIKE:
                if (isFieldType(filterField, String.class)) {
                    String value = getStringValueFrom(descriptor);
                    return criteriaBuilder.like(
                            criteriaBuilder.lower((Expression<String>) field),
                            "%" + value.toLowerCase() + "%");
                }

            case START_WITH:
                if (isFieldType(filterField, String.class)) {
                    String value = getStringValueFrom(descriptor);
                    return criteriaBuilder.like((Expression<String>) field, value + "%");
                }

            case LT:
                if (isFieldType(filterField, Long.class)) {
                    Long value = getLongValueFrom(descriptor);
                    return criteriaBuilder.lessThan((Expression<Long>) field, value);
                } else if (isFieldType(filterField, Integer.class)) {
                    Integer value = getIntegerValueFrom(descriptor);
                    return criteriaBuilder.lessThan((Expression<Integer>) field, value);
                } else if (isFieldType(filterField, LocalDate.class)) {
                    LocalDate value = getLocalDateValueFrom(descriptor);
                    return criteriaBuilder.lessThan((Expression<LocalDate>) field, value);
                } else if (isFieldType(filterField, LocalDateTime.class)) {
                    LocalDateTime value = getLocalDateTimeValueFrom(descriptor);
                    return criteriaBuilder.lessThan((Expression<LocalDateTime>) field, value);
                }

            case LTE:
                if (isFieldType(filterField, Long.class)) {
                    Long value = getLongValueFrom(descriptor);
                    return criteriaBuilder.lessThanOrEqualTo((Expression<Long>) field, value);
                } else if (isFieldType(filterField, Integer.class)) {
                    Integer value = getIntegerValueFrom(descriptor);
                    return criteriaBuilder.lessThanOrEqualTo((Expression<Integer>) field, value);
                } else if (isFieldType(filterField, LocalDate.class)) {
                    LocalDate value = getLocalDateValueFrom(descriptor);
                    return criteriaBuilder.lessThanOrEqualTo((Expression<LocalDate>) field, value);
                } else if (isFieldType(filterField, LocalDateTime.class)) {
                    LocalDateTime value = getLocalDateTimeValueFrom(descriptor);
                    return criteriaBuilder.lessThanOrEqualTo((Expression<LocalDateTime>) field, value);
                }

            case NOT_EQ:
                if (isFieldType(filterField, Long.class)) {
                    Long value = getLongValueFrom(descriptor);
                    return criteriaBuilder.notEqual(field, value);
                } else if (isFieldType(filterField, Integer.class)) {
                    Integer value = getIntegerValueFrom(descriptor);
                    return criteriaBuilder.notEqual(field, value);
                } else if (isFieldType(filterField, LocalDate.class)) {
                    LocalDate value = getLocalDateValueFrom(descriptor);
                    return criteriaBuilder.notEqual(field, value);
                } else if (isFieldType(filterField, Boolean.class)) {
                    Boolean value = getBooleanValueFrom(descriptor);
                    return criteriaBuilder.notEqual(field, value);
                }

            case IN:
                String filterValue = (String) descriptor.getValue();
                if (StringUtils.isNotBlank(filterValue)) {
                    String[] arr = filterValue.split(IN_SPLITERATOR);
                    Class<?> clazz = filterField.getClassType();

                    if (clazz.equals(Long.class)) {
                        List<Long> valueList = new ArrayList<>();
                        for (String s : arr) {
                            valueList.add(Long.valueOf(s));
                        }
                        return field.in(valueList);
                    } else if (clazz.equals(String.class)) {
                        List<String> valueList = new ArrayList<>(Arrays.asList(arr));
                        return field.in(valueList);
                    } else {
                        log.warn("Unsupported type " + clazz.getName() + " for 'in' operator");
                    }
                }
            case NOT_IN:
                String filterValueNotIn = (String) descriptor.getValue();
                if (StringUtils.isNotBlank(filterValueNotIn)) {
                    String[] arr = filterValueNotIn.split(IN_SPLITERATOR);
                    Class<?> clazz = filterField.getClassType();
                    List<Object> values = new ArrayList<>();

                    for (String s : arr) {
                        if (clazz.equals(Long.class)) {
                            values.add(Long.valueOf(s));
                        } else if (clazz.equals(String.class)) {
                            values.add(s);
                        } else {
                            log.warn("Unsupported type " + clazz.getName() + " for 'not_in' operator");
                        }
                    }
                    return field.in(values).not();
                }

            case IS_NOT_NULL:
                return criteriaBuilder.isNotNull(field);

            case IS_NULL:
                return criteriaBuilder.isNull(field);

            default:
                break;
        }

        log.warn("Operator " + descriptor.getOperator() + " was not found! ");

        return null;
    }

    /**
     * Returns the attribute of an entity class
     *
     * @param queryField      used to get the expression from different entities
     * @param root            is the Entity
     * @param criteriaBuilder can be used with root to use custom sql functions (year, avg ...)
     * @return the attribute(FilterField) of an entity class
     */
    private FilterField getExpressionField(IQueryField queryField, Root<T> root, CriteriaBuilder criteriaBuilder) {
        FilterField filterField = queryField.getExpressionField(root, criteriaBuilder);
        if (filterField == null) {
            throw new RuntimeException(queryField.getFieldName() + " is not supported!");
        }
        return filterField;
    }

    private LocalDate getLocalDateValueFrom(FilterDescriptor<?, ?> filterDescriptor) {
        return LocalDate.parse((String) filterDescriptor.getValue());
//        return LocalDate.parse((String) filterDescriptor.getValue(), format); in case of custom format
    }

    private Integer getIntegerValueFrom(FilterDescriptor<?, ?> filterDescriptor) {
        return Integer.parseInt(String.valueOf(filterDescriptor.getValue()));
    }

    private Long getLongValueFrom(FilterDescriptor<?, ?> filterDescriptor) {
        return Long.parseLong(String.valueOf(filterDescriptor.getValue()));
    }

    private String getStringValueFrom(FilterDescriptor<?, ?> filterDescriptor) {
        return (String) filterDescriptor.getValue();
    }

    private Boolean getBooleanValueFrom(FilterDescriptor<?, ?> filterDescriptor) {
        return Boolean.parseBoolean(String.valueOf(filterDescriptor.getValue()));
    }

    private Double getDoubleValueFrom(FilterDescriptor<?, ?> filterDescriptor) {
        return Double.parseDouble(String.valueOf(filterDescriptor.getValue()));
    }

    private LocalDateTime getLocalDateTimeValueFrom(FilterDescriptor<?, ?> filterDescriptor) {
        return LocalDateTime.parse(String.valueOf(filterDescriptor.getValue()));
//        return LocalDateTime.parse(String.valueOf(filterDescriptor.getValue()), format); in case of custom format
    }

    private boolean isFieldType(FilterField field, Class clazz) {
        return field.getClassType().equals(clazz);
    }
}
