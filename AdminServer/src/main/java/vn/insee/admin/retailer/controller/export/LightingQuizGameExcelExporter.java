package vn.insee.admin.retailer.controller.export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.insee.admin.retailer.controller.dto.StockFormDTO;
import vn.insee.admin.retailer.controller.dto.lq.LQFormDTO;
import vn.insee.admin.retailer.util.City;
import vn.insee.common.status.StatusForm;
import vn.insee.util.insee.CementManager;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class LightingQuizGameExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<LQFormDTO> lqFormDTOS;

    public LightingQuizGameExcelExporter(List<LQFormDTO> lqFormDTOS) {
        this.lqFormDTOS = lqFormDTOS;
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
        createCell(row, 6, "POINT", style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (LQFormDTO lqFormDTO : lqFormDTOS) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, lqFormDTO.getUser().getName(), style);
            createCell(row, columnCount++, lqFormDTO.getUser().getInseeId(), style);
            createCell(row, columnCount++, lqFormDTO.getUser().getPhone(), style);
            createCell(row, columnCount++, City.findCityById(lqFormDTO.getUser().getCityId()), style);
            createCell(row, columnCount++, City.findDistrictById(lqFormDTO.getUser().getDistrictId()), style);
            createCell(row, columnCount++, StatusForm.findByName(lqFormDTO.getStatus()), style);
            createCell(row, columnCount++, lqFormDTO.getPoint(), style);
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
