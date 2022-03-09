package vn.insee.admin.retailer.controller.export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.insee.admin.retailer.util.City;
import vn.insee.jpa.entity.UserEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<UserEntity> listUsers;

    public UserExcelExporter(List<UserEntity> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "AVATAR", style);
        createCell(row, 1, "PHONE", style);
        createCell(row, 2, "INSEE ID", style);
        createCell(row, 3, "NAME", style);
        createCell(row, 4, "CITY", style);
        createCell(row, 5, "DISTRICT", style);
        createCell(row, 6, "ADDRESS", style);
        createCell(row, 7, "DATE", style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (UserEntity user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, user.getAvatar(), style);
            createCell(row, columnCount++, user.getPhone(), style);
            createCell(row, columnCount++, user.getInseeId(), style);
            createCell(row, columnCount++, user.getName(), style);
            createCell(row, columnCount++, (user.getCityId() != null && user.getCityId() > 0 ) ? City.findCityById(user.getCityId()) : "", style);
            createCell(row, columnCount++, (user.getDistrictId() != null && user.getDistrictId() > 0 ) ? City.findDistrictById(user.getDistrictId()) : "", style);
            createCell(row, columnCount++, user.getAddress(), style);
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm");

            String currentDateTime = dateFormatter.format(new Date(user.getCreatedTime().toEpochSecond() * 1000));
            createCell(row, columnCount++, currentDateTime, style);
        }
    }


    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

}
