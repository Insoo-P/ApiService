package com.insoo.project;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/excel")
public class ExcelConvertController {

    @PostMapping("/upload")
    public ExcelResponse uploadExcel(@RequestParam("file") MultipartFile file) {
        ExcelResponse response = new ExcelResponse();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // 엑셀 파일을 JSON 배열로 변환
            List<Map<String, String>> jsonData = convertSheetToJson(sheet);

            // 엑셀 파일을 CSV 문자열로 변환
//            String csvData = convertSheetToCsv(sheet);

            response.setJsonResult(jsonData);
//            response.setCsvResult(csvData);

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private List<Map<String, String>> convertSheetToJson(Sheet sheet) {
        List<Map<String, String>> dataList = new ArrayList<>();
        Row headerRow = sheet.getRow(0); // 첫 번째 행을 키로 사용

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            Map<String, String> rowData = new HashMap<>();

            for (int cellIndex = 0; cellIndex < headerRow.getLastCellNum(); cellIndex++) {
                Cell headerCell = headerRow.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                headerCell.setCellType(CellType.STRING);
                cell.setCellType(CellType.STRING);

                String key = headerCell.getStringCellValue();
                String value = cell.getStringCellValue();

                rowData.put(key, value);
            }

            dataList.add(rowData);
        }

        return dataList;
    }

//    private String convertSheetToCsv(Sheet sheet) {
//        StringBuilder csvData = new StringBuilder();
//
//        for (Row row : sheet) {
//            for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
//                Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//                cell.setCellType(CellType.STRING);
//                csvData.append(cell.getStringCellValue()).append(",");
//            }
//            csvData.deleteCharAt(csvData.length() - 1); // 마지막 쉼표 제거
//            csvData.append("\n");
//        }
//
//        return csvData.toString();
//    }

    // Response 클래스 정의
    static class ExcelResponse {
        private List<Map<String, String>> jsonResult;
        private String csvResult;

        // Getters and setters
        public List<Map<String, String>> getJsonResult() {
            return jsonResult;
        }

        public void setJsonResult(List<Map<String, String>> jsonResult) {
            this.jsonResult = jsonResult;
        }

        public String getCsvResult() {
            return csvResult;
        }

        public void setCsvResult(String csvResult) {
            this.csvResult = csvResult;
        }
    }
}