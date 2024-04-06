package com.gisdev.dea.util.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class CellStyleBuilder {

    private Workbook workbook;
    private Short fgColor;
    private Short bgColor;
    private HorizontalAlignment horizontalAlignment;
    private VerticalAlignment verticalAlignment;
    private boolean bold;
    private String fontName;
    private boolean italic;
    private BorderStyle topBorder;
    private BorderStyle rightBorder;
    private BorderStyle bottomBorder;
    private BorderStyle leftBorder;
    private boolean wrapText;
    private String dataFormat;
    private Short fontSize;
    private Short rotation;
    private Byte underline;

    public CellStyleBuilder(Workbook workbook) {
        this.workbook = workbook;
    }

    public static CellStyleBuilder get(Workbook workbook) {
        return new CellStyleBuilder(workbook);
    }

    public CellStyleBuilder withForegroundColor(Short fgColor) {
        this.fgColor = fgColor;
        return this;
    }

    public CellStyleBuilder withBackgroundColor(Short bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public CellStyleBuilder withFontName(String fontName) {
        this.fontName = fontName;
        return this;
    }

    public CellStyleBuilder withBold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public CellStyleBuilder withItalic(boolean italic) {
        this.italic = italic;
        return this;
    }

    public CellStyleBuilder withHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        return this;
    }

    public CellStyleBuilder withVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
        return this;
    }

    public CellStyleBuilder withBorderTop(BorderStyle borderStyle) {
        this.topBorder = borderStyle;
        return this;
    }

    public CellStyleBuilder withBorderRight(BorderStyle borderStyle) {
        this.rightBorder = borderStyle;
        return this;
    }

    public CellStyleBuilder withBorderLeft(BorderStyle borderStyle) {
        this.leftBorder = borderStyle;
        return this;
    }

    public CellStyleBuilder withBorderBottom(BorderStyle borderStyle) {
        this.bottomBorder = borderStyle;
        return this;
    }

    public CellStyleBuilder withWrapText(boolean wrapText) {
        this.wrapText = wrapText;
        return this;
    }

    public CellStyleBuilder withFontSize(short fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public CellStyleBuilder withAllBorders(BorderStyle borderStyle) {
        this.topBorder = borderStyle;
        this.rightBorder = borderStyle;
        this.leftBorder = borderStyle;
        this.bottomBorder = borderStyle;
        return this;
    }

    public CellStyleBuilder withLeftAndRightBorders(BorderStyle borderStyle) {
        this.rightBorder = borderStyle;
        this.leftBorder = borderStyle;
        return this;
    }


    public CellStyleBuilder withDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
        return this;
    }

    public CellStyleBuilder withRotation(Short rotation) {
        this.rotation = rotation;
        return this;
    }

    public CellStyleBuilder withUnderline(Byte underline) {
        this.underline = underline;
        return this;
    }

    public CellStyle build() {

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();

        try {
            if (fgColor != null) {
                font.setColor(fgColor);
            }

            if (bgColor != null) {
                cellStyle.setFillForegroundColor(bgColor);
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            }

            if (horizontalAlignment != null) {
                cellStyle.setAlignment(horizontalAlignment);
            }

            if (verticalAlignment != null) {
                cellStyle.setVerticalAlignment(verticalAlignment);
            }

            if (fontName != null) {
                font.setFontName(fontName);
            }

            font.setBold(bold);
            font.setItalic(italic);

            if (dataFormat != null) {
                cellStyle.setDataFormat(workbook.createDataFormat().getFormat(dataFormat));
            }

            if (leftBorder != null) {
                cellStyle.setBorderLeft(leftBorder);
            }

            if (rightBorder != null) {
                cellStyle.setBorderRight(rightBorder);
            }

            if (topBorder != null) {
                cellStyle.setBorderTop(topBorder);
            }

            if (bottomBorder != null) {
                cellStyle.setBorderBottom(bottomBorder);
            }

            if (fontSize != null) {
                font.setFontHeightInPoints(fontSize);
            }

            if (rotation != null) {
                cellStyle.setRotation(rotation);
            }

            if (underline != null) {
                font.setUnderline(underline);
            }

            cellStyle.setFont(font);
            cellStyle.setWrapText(wrapText);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellStyle;
    }

}
