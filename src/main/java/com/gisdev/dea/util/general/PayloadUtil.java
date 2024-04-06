package com.gisdev.dea.util.general;

import com.gisdev.dea.exception.BadRequestException;
import com.gisdev.dea.exception.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Collection;
import java.util.function.Predicate;

public class PayloadUtil {

    private PayloadUtil() {
    }

    public static void assertNotFound(Object payload, String message) {
        if (payload == null) {
            throw new NotFoundException(message);
        }
    }

    public static void assertNotNull(Object payload, String message) {
        if (payload == null) {
            throwBadRequest(message);
        }
    }

    public static void assertNotEmpty(String property, String message) {
        if (StringUtils.isEmpty(property)) {
            throwBadRequest(message);
        }
    }

    public static void assertNotEmpty(Collection property, String message) {
        if (CollectionUtils.isEmpty(property)) {
            throwBadRequest(message);
        }
    }

    public static <T> void assertTrue(T t, Predicate<T> predicate, String errorMessage) {
        if (!predicate.test(t)) {
            throwBadRequest(errorMessage);
        }
    }

    public static void assertTrue(boolean predicate, String errorMessage) {
        if (!predicate) {
            throwBadRequest(errorMessage);
        }
    }

    public static void assertBetween(LocalDate startDate, LocalDate endDate, LocalDate date, String errorMessage) {
        boolean isDateWithinTrainingRangeDate = date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
        if (!isDateWithinTrainingRangeDate) {
            throwBadRequest(errorMessage);
        }
    }

    private static void throwBadRequest(String errorMessage) {
        throw new BadRequestException(errorMessage);
    }
}
