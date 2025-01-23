package com.dev.stockmarketsystem.utils;

import com.dev.stockmarketsystem.models.Stock;
import com.dev.stockmarketsystem.models.Transaction;
import com.dev.stockmarketsystem.repositories.StockRepository;
import com.dev.stockmarketsystem.repositories.TransactionRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ExcelExporter {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public ByteArrayInputStream exportDataToExcel() {
        String[] stockColumns = {"ID", "Name", "Symbol", "Price", "Quantity", "Active"};
        String[] transactionColumns = {"ID", "User", "Stock", "Type", "Quantity", "Price", "Commission", "Date"};
        
        List<Stock> stocks = stockRepository.findAll();
        List<Transaction> transactions = transactionRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Data");

            // Create header row for stocks
            Row stockHeaderRow = sheet.createRow(0);
            for (int i = 0; i < stockColumns.length; i++) {
                Cell cell = stockHeaderRow.createCell(i);
                cell.setCellValue(stockColumns[i]);
            }

            // Create data rows for stocks
            int rowIdx = 1;
            for (Stock stock : stocks) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(stock.getId());
                row.createCell(1).setCellValue(stock.getName());
                row.createCell(2).setCellValue(stock.getSymbol());
                row.createCell(3).setCellValue(stock.getPrice());
                row.createCell(4).setCellValue(stock.getQuantity());
                row.createCell(5).setCellValue(stock.isActive());
            }

            // Create header row for transactions
            Row transactionHeaderRow = sheet.createRow(rowIdx++);
            for (int i = 0; i < transactionColumns.length; i++) {
                Cell cell = transactionHeaderRow.createCell(i);
                cell.setCellValue(transactionColumns[i]);
            }

            // Create data rows for transactions
            for (Transaction transaction : transactions) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(transaction.getId());
                row.createCell(1).setCellValue(transaction.getUser().getUsername());
                row.createCell(2).setCellValue(transaction.getStock().getName());
                row.createCell(3).setCellValue(transaction.getType().toString());
                row.createCell(4).setCellValue(transaction.getQuantity());
                row.createCell(5).setCellValue(transaction.getPrice());
                row.createCell(6).setCellValue(transaction.getCommission());
                row.createCell(7).setCellValue(transaction.getTransactionDate().toString());
            }

            workbook.write(out);

            // Save the file to the specified directory
            String filePath = "C:/Users/wwwan/OneDrive/Desktop/SpringBoot/stockmarketsystem/exports/data.xlsx";
            Files.createDirectories(Paths.get("C:/Users/wwwan/OneDrive/Desktop/SpringBoot/stockmarketsystem/exports"));
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(out.toByteArray());
            }

            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to Excel file: " + e.getMessage());
        }
    }
}
