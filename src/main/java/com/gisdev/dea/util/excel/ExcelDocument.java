package com.gisdev.dea.util.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

@Slf4j
public class ExcelDocument {
    
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelDocument() {
        workbook = new XSSFWorkbook();
    }

    public XSSFSheet createSheet(String name) {
        sheet = workbook.createSheet(name);
        return sheet;
    }

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    public XSSFSheet getSheet() {
        return sheet;
    }

    public CellStyle createThickCell() {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THICK);
        cellStyle.setBorderTop(BorderStyle.THICK);
        cellStyle.setBorderRight(BorderStyle.THICK);
        cellStyle.setBorderLeft(BorderStyle.THICK);
        return cellStyle;
    }

    public CellStyle createThinCell() {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    public CellStyle createThinCellLeftAlignment() {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    public void createBorderBound(int top, int bottom, int left, int right, BorderStyle bs) {
        for (int i = top; i <= bottom; ++i) {
            XSSFRow rowHeader = sheet.createRow(i);
            for (int j = left; j <= right; j++) {
                Cell cell = rowHeader.createCell(j);
                CellStyle cellStyle = workbook.createCellStyle();
                if (i == top) {
                    cellStyle.setBorderTop(bs);
                } else if (i == bottom) {
                    cellStyle.setBorderBottom(bs);
                }
                if (j == left) {
                    cellStyle.setBorderLeft(bs);
                } else if (j == right) {
                    cellStyle.setBorderRight(bs);
                }
                cell.setCellStyle(cellStyle);
            }
        }
    }

    public void insertPng(String filePath, int row1, int col1, int row2, int col2) {
        int pictureIdx = 0;
        try {
            InputStream is = new FileInputStream(filePath);
            byte[] bytes = IOUtils.toByteArray(is);
            pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            is.close();
        } catch (Exception ex) {
            log.error("ExcelDocument.insertPng: " +  ex);
            return;
        }

        CreationHelper helper = workbook.getCreationHelper();
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(col1);
        anchor.setRow1(row1);
        anchor.setCol2(col2);
        anchor.setRow2(row2);
        Picture pict = drawing.createPicture(anchor, pictureIdx);
    }

    public void createFile(String fullPath) {
        try {
            FileOutputStream outputStream = new FileOutputStream(fullPath);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            log.error(e.toString());
            e.printStackTrace();

        } catch (IOException e) {
            log.error("ExcelDocument.createFile: " + e);
            e.printStackTrace();
        }
    }

    public Cell createCell(String value, Row row, int columnIndex, CellStyle... style) {
        Cell cell = row.createCell(columnIndex);
        if (value != null) cell.setCellValue(value);
        if (style != null && style.length > 0) cell.setCellStyle(style[0]);

        return cell;
    }

    public Cell createCell(Integer value, Row row, int columnIndex, CellStyle... style) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value != null ? value : 0);
        if (style != null && style.length > 0) cell.setCellStyle(style[0]);

        return cell;
    }

    public Cell createCell(Double value, Row row, int columnIndex, CellStyle... style) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value != null ? value : 0.0);
        if (style != null && style.length > 0) cell.setCellStyle(style[0]);

        return cell;
    }

    public Cell createFormulaCell(String formula, Row row, int columnIndex, CellStyle... style) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellFormula(formula);
        if (style != null && style.length > 0) cell.setCellStyle(style[0]);

        return cell;
    }

    public CellRangeAddress createMergedRegions(Sheet sheet, Row rowHeader, String cellValue, int startRow, int endRow,
                                                int colStart, int colEnd, CellStyle headerStyle) {
        CellRangeAddress cellRangeAddress = new CellRangeAddress(startRow, endRow, colStart, colEnd);
        sheet.addMergedRegion(cellRangeAddress);
        Cell cellTittle = rowHeader.createCell(colStart);
        cellTittle.setCellValue(cellValue);
        cellTittle.setCellStyle(headerStyle);

        return cellRangeAddress;
    }

    public void setBordersToMergedZone(BorderStyle borderStyle, CellRangeAddress cellRangeAddress, XSSFSheet sheet) {
        RegionUtil.setBorderTop(borderStyle, cellRangeAddress, sheet);
        RegionUtil.setBorderLeft(borderStyle, cellRangeAddress, sheet);
        RegionUtil.setBorderRight(borderStyle, cellRangeAddress, sheet);
        RegionUtil.setBorderBottom(borderStyle, cellRangeAddress, sheet);
    }

    public void encryptFile(String password, String fullpath) {

        File xlsxFile = new File(fullpath);

        try (POIFSFileSystem fs = new POIFSFileSystem()) {

            EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
            Encryptor encryptor = info.getEncryptor();
            encryptor.confirmPassword(password);

            try (OPCPackage opc = OPCPackage.open(xlsxFile, PackageAccess.READ_WRITE);
                 OutputStream os = encryptor.getDataStream(fs)) {
                opc.save(os);
            }
            try (FileOutputStream fos = new FileOutputStream(xlsxFile)) {
                fs.writeFilesystem(fos);
            } catch (FileNotFoundException fileNotFoundException) {
                log.error(fileNotFoundException.toString());
            } catch (IOException ioException) {
                log.error(ioException.toString());
            }
        } catch (IOException | GeneralSecurityException | InvalidFormatException ioException) {
            ioException.printStackTrace();
        }
        log.info("Protected Excel(.xlsx) file has been created successfully.");
    }
}
