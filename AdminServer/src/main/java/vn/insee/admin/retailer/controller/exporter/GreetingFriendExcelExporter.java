package vn.insee.admin.retailer.controller.exporter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.insee.admin.retailer.controller.dto.GreetingFriendFormDTO;
import vn.insee.admin.retailer.util.City;
import vn.insee.common.status.StatusForm;
import vn.insee.util.insee.CementManager;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GreetingFriendExcelExporter {
    private static final DateFormat dateFormatter = new SimpleDateFormat("HH:mm yyyy-MM-dd");

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<GreetingFriendFormDTO> dtos;

    public GreetingFriendExcelExporter(List<GreetingFriendFormDTO> dtos) {
        this.dtos = dtos;
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

        createCell(row, 0, "NAME", style);
        createCell(row, 1, "INSEE ID", style);
        createCell(row, 2, "PHONE", style);
        createCell(row, 3, "CITY", style);
        createCell(row, 4, "DISTRICT", style);
        createCell(row, 5, "STATUS", style);
        createCell(row, 6, "BAGS", style);
        createCell(row, 7, "CEMENTS", style);
        createCell(row, 8, "REGISTERED TIME", style);
        createCell(row, 9, "SUBMIT TIME", style);

    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (GreetingFriendFormDTO dto : dtos) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, dto.getUser().getName(), style);
            createCell(row, columnCount++, dto.getUser().getInseeId(), style);
            createCell(row, columnCount++, dto.getUser().getPhone(), style);
            createCell(row, columnCount++, City.findCityById(dto.getUser().getCityId()), style);
            createCell(row, columnCount++, City.findDistrictById(dto.getUser().getDistrictId()), style);
            createCell(row, columnCount++, StatusForm.findByName(dto.getStatus()), style);
            createCell(row, columnCount++, dto.getBags(), style);
            List<Integer> cements = dto.getCements();
            if (cements != null) {
                String strCements = cements.stream().map(c -> CementManager.findById(c).getName()).collect(Collectors.joining(","));
                createCell(row, columnCount++, strCements, style);
            }else {
                createCell(row, columnCount++, "", style);
            }
            createCell(row, columnCount++, dateFormatter.format(new Date(dto.getCreatedTime() * 1000)), style);
            createCell(row, columnCount++, dateFormatter.format(new Date(dto.getTime() * 1000)), style);
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
