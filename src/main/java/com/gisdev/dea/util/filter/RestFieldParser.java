package com.gisdev.dea.util.filter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gisdev.dea.util.constant.RestConstants.IN_SPLITERATOR;
import static com.gisdev.dea.util.constant.RestConstants.SORT_ASC;
import static com.gisdev.dea.util.constant.RestConstants.SORT_DESC;
import static com.gisdev.dea.util.constant.RestConstants.SORT_SPLITERATOR;

/**
 * @param <T> Enum with the supported fields
 */
@Slf4j
@AllArgsConstructor
public class RestFieldParser<T extends IQueryField> {

    private final T[] fieldValues;

    public List<FilterDescriptor<T, ?>> parseFilters(List<String> filters) {
        List<FilterDescriptor<T, ?>> result = new ArrayList<>();
        for (String filter : filters) {
            if (StringUtils.isBlank(filter)) {
                continue;
            }
            for (FilterOperator operator : FilterOperator.values()) {
                String[] tokens = filter.split(operator.getOperator());
                if (tokens.length > 1) {
                    String fieldAlias = tokens[0];
                    String serializedString = tokens[1];

                    T field = fromRestAlias(fieldAlias);
                    if (field != null) {
                        result.add(new FilterDescriptor<>(field, fieldAlias, operator, serializedString));
                    }
                    break;
                }
            }
        }

        return result;
    }

    private T fromRestAlias(String fieldAlias) {
        return Stream.of(fieldValues).filter(candidate -> Objects.equals(candidate.getFieldName(), fieldAlias)).findFirst()
                .orElse(null);
    }

    public List<Sort.Order> parseSortOrders(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();

        if (ObjectUtils.isEmpty(sort)) {
            return orders;
        }

        for (String sortOrder : sort) {
            String[] sortArray = sortOrder.split(SORT_SPLITERATOR);

            if (sortArray.length != 2) {
                log.error("Sort format is not valid: {}", sortOrder);
                continue;
            }

            String fieldAlias = sortArray[0];
            String sortFieldOrder = sortArray[1];

            T field = fromRestAlias(fieldAlias);
            if (field != null) {
                orders.add(new Sort.Order(getSortDirection(sortFieldOrder), field.getSortName()));
            }
        }
        return orders;
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals(SORT_ASC)) {
            return Sort.Direction.ASC;
        } else if (direction.equals(SORT_DESC)) {
            return Sort.Direction.DESC;
        }

        log.error("Defaulting to asc order for input: {}", direction);
        return Sort.Direction.ASC;
    }

    public static String buildInPayload(Object...objects) {
        return Stream.of(objects).map(Object::toString)
                .collect(Collectors.joining(IN_SPLITERATOR));
    }

}