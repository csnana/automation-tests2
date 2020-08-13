package utilities.readers;


/**
 * @author Sajeev Rajagopalan on 04/12/2016.
 * read and write xls using POI
 */


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static org.apache.poi.ss.usermodel.Cell.*;


public class ExcelReader {

    private static final Logger log = LoggerFactory.getLogger(ExcelReader.class);
    private static String filePath = System.getProperty("user.dir") + "/target" +
            "/test-classes/com/tcs/project/integration/";

    private HSSFSheet sheet;

    //Get starting cell position of any value
    public static Object findCellindex(String fileName, int sheetNo, int sheetIndex, String text) {

        HSSFSheet sheet;
        sheet = new ExcelReader()
                .sheetOpen(fileName, sheetNo);

        for (Row row : sheet) {
            for (Cell cell : row) {

                if (cell.getCellType() == CELL_TYPE_STRING) {
                    if (text.equals(cell.getStringCellValue())) {
                        return cell.getRowIndex() + "-" + cell.getColumnIndex();

                    }
                }
            }
        }
        return null;
    }

    public static int findRow(String fileName, int sheetNo, int sheetIndex, String cellContent, int row) {


        HSSFSheet sheet;
        sheet = new ExcelReader()
                .sheetOpen(fileName, sheetNo);

        int lastRow = (Integer) sheet.getLastRowNum();
        Cell cell = null;

        for (int i = row; i <= lastRow; i++) {

            if (sheet.getRow(i) != null && sheet.getRow(i).getCell(0) != null) {
                cell = sheet.getRow(i).getCell(0);
                String searchStr = cell.getRichStringCellValue().getString();
                if (cell.getCellType() == CELL_TYPE_STRING) {
                    if (cell.getRichStringCellValue().getString().matches(cellContent)) {
                        int rowNum = cell.getRowIndex();
                        System.out.println("rowNum::" + rowNum);
                        return rowNum;
                    }
                }
            }
        }
        return lastRow;
    }

    private HSSFSheet sheetOpen(String fileName, int sheetNo) {

        try {
            InputStream file = new FileInputStream(filePath + fileName);
            log.info("File status: " + file.available());
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            sheet = workbook.getSheetAt(sheetNo);
            file.close();

        } catch (IOException ioe) {
            throw new ExceptionInInitializerError(ioe);
        }
        return sheet;
    }

    public int getUsedRangeOfSheet(String fileName, int sheetNo) {
        log.info("Reading worksheet used range : " + fileName);
        return new ExcelReader()
                .sheetOpen(fileName, sheetNo)
                .getLastRowNum();
    }

    public Object getTestDataValue(String fileName, int sheetNo, int row, int column) {


        log.info("Reading worksheet : " + fileName);
        Object sheetValue;
        Cell cell = new ExcelReader()
                .sheetOpen(fileName, sheetNo).getRow(row).getCell(column);

        switch (cell.getCellType()) {

            case CELL_TYPE_NUMERIC:
                sheetValue = Math.round(cell.getNumericCellValue());
                break;

            case CELL_TYPE_STRING:
                sheetValue = cell.getStringCellValue();
                break;

            case CELL_TYPE_FORMULA:
                sheetValue = cell.getCellFormula();
                sheetValue = Math.round(cell.getNumericCellValue());
                break;

            case CELL_TYPE_BLANK:
                sheetValue = cell.getNumericCellValue();
                break;

            case CELL_TYPE_BOOLEAN:
                sheetValue = Math.round(cell.getNumericCellValue());
                break;

            case CELL_TYPE_ERROR:
                sheetValue = Math.round(cell.getNumericCellValue());
                break;

            default:
                sheetValue = 0;
                break;
        }
        log.info("TestData : " + sheetValue);
        return sheetValue;

    }

    public void setTestDataValue(String fileName,
                                 int sheetNo,
                                 String Mssg,
                                 int row,
                                 int column) throws IOException {


        log.info("Updating worksheet : " + fileName);
        //Read the spreadsheet that needs to be updated
        FileInputStream input_document = new FileInputStream(new File(filePath + fileName));
        //Access the workbook
        HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document);
        //Access the worksheet, so that we can update / modify it.
        HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(sheetNo);

        if (my_worksheet.getRow(row) != null &&
                my_worksheet.getRow(row).getCell(column) != null) {

            my_worksheet.getRow(row).getCell(column).setCellValue(Mssg);


        } else {
            my_worksheet.createRow(row).createCell(column).setCellValue(Mssg);
        }

        //Close the InputStream
        input_document.close();
        //Open FileOutputStream to write updates
        FileOutputStream output_file = new FileOutputStream(new File(filePath + fileName));

        //write changes
        my_xls_workbook.write(output_file);
        //close the stream
        output_file.close();


    }


}