package ir.sandok;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sandokminer.TGJUMiner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        TGJUMiner tgjuMiner = new TGJUMiner();
        ArrayList<ArrayList<String>> data = tgjuMiner.getData(4,111,"https://www.tgju.org/profile/price_dollar_rl/history");


        String filePath = "C:\\Users\\PC\\Desktop\\java\\sandok-miner\\src\\main\\resources\\chromeDriver\\DOLLAR.xlsx"; // Path to the output Excel file

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");

            int rowNum = 0;
            for (ArrayList<String> rowData : data) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (String cellData : rowData) {
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(cellData);
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
                System.out.println("Excel file created successfully!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}