package com.ankit.service;

import com.ankit.exception.FileProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileProcessingService {

    public ByteArrayOutputStream convertFileToPdf(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) {
            throw new FileProcessingException("Invalid file type");
        }

        try {
            if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                return convertExcelToPdf(file.getInputStream());
            } else if (contentType.equals("application/msword")) {
                return convertWordToPdf(file.getInputStream());
            } else if (contentType.equals("application/json")) {
                return convertJsonToPdf(file.getInputStream());
            } else if (contentType.equals("text/plain")) {
                return convertTextToPdf(file.getInputStream());
            } else {
                throw new FileProcessingException("Unsupported file type");
            }
        } catch (IOException | DocumentException e) {
            throw new FileProcessingException("Failed to process the file", e);
        }
    }

    private ByteArrayOutputStream convertExcelToPdf(InputStream inputStream) throws IOException, DocumentException {
        try (Workbook workbook = new XSSFWorkbook(inputStream);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                document.add(new Paragraph(sheet.getSheetName()));
                PdfPTable table = new PdfPTable(sheet.getRow(0).getPhysicalNumberOfCells());

                for (Row row : sheet) {
                    for (Cell cell : row) {
                        table.addCell(getCellContent(cell));
                    }
                }
                document.add(table);
                document.newPage();
            }

            document.close();
            return outputStream;
        }
    }

    private ByteArrayOutputStream convertWordToPdf(InputStream inputStream) throws IOException, DocumentException {
        try (HWPFDocument document = new HWPFDocument(inputStream);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Document pdfDocument = new Document();
            PdfWriter.getInstance(pdfDocument, outputStream);
            pdfDocument.open();

            Range range = document.getRange();
            pdfDocument.add(new Paragraph(range.text()));

            pdfDocument.close();
            return outputStream;
        }
    }

    private ByteArrayOutputStream convertJsonToPdf(InputStream inputStream) throws IOException, DocumentException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        document.add(new Paragraph(jsonNode.toString()));

        document.close();
        return outputStream;
    }

    private ByteArrayOutputStream convertTextToPdf(InputStream inputStream) throws IOException, DocumentException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                document.add(new Paragraph(new String(buffer, 0, bytesRead)));
            }

            document.close();
            return outputStream;
        }
    }

    private String getCellContent(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}


