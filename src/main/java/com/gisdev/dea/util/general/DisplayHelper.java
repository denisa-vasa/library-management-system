package com.gisdev.dea.util.general;

import com.gisdev.dea.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class DisplayHelper {

    public static String formatDate(LocalDate localDate, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(localDate);
    }

    public static String formatDate(LocalDate localDate) {
        return formatDate(localDate, "dd-MM-yyyy");
    }

    public static String formatDateFromDateTime(LocalDateTime localDatetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return formatter.format(localDatetime);
    }

    public static String numberFormatForValues(Double value) {
        NumberFormat formatter = new DecimalFormat("#,##0.00");
        return formatter.format(value);
    }

    public static String doubleDisplayerWith3Decimals(double nr2Display) {
        DecimalFormat displayFormat = new DecimalFormat("#0.000");
        return displayFormat.format(nr2Display);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return formatter.format(dateTime);
    }

    public static LocalDateTime convertFromStringToLocalDateTime(String fieldName, String dateString, boolean required) {
        if (required && StringUtils.isEmpty(dateString)) {
            throw new BadRequestException(String.format("Fusha %s nuk duhet te jete bosh!", fieldName));
        }

        try {
            if (StringUtils.isEmpty(dateString)) {
                return null;
            }
            return LocalDateTime.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new BadRequestException(String.format("Fusha %s nuk eshte e vlefshme!", fieldName));
        }
    }

    public static LocalDate convertFromStringToLocalDate(String fieldName, String dateString, boolean required) {
        if (required && StringUtils.isEmpty(dateString)) {
            throw new BadRequestException(String.format("Fusha %s nuk duhet te jete bosh!", fieldName));
        }

        try {
            if (StringUtils.isEmpty(dateString)) {
                return null;
            }
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new BadRequestException(String.format("Fusha %s nuk eshte e vlefshme!", fieldName));
        }
    }

    public static <E extends Enum<E>> E convertFromStringToEnum(String source, Class<E> classType) {
        if (source.isEmpty() || source.isBlank()) {
            throw new BadRequestException(classType.getSimpleName() + " nuk duhet te jete bosh!");
        }
        try {
            return Enum.valueOf(classType, source.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Kjo vlere '" + source + "' nuk i perket konstanteve te ENUM " + classType.getSimpleName() + " !");
        }
    }

    public static int getDaysBetweenInclusive(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    public static boolean retrieveNotNull(Boolean value) {
        if(value == null) {
            return false;
        }
        return value;
    }
}
