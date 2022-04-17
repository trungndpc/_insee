package vn.insee.admin.retailer.controller.importer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.insee.admin.retailer.util.City;
import vn.insee.jpa.entity.UserEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerExcelImporter {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final CustomerExcelImporter INSTANCE = new CustomerExcelImporter();

    public List<UserEntity> read(InputStream inputStream) throws Exception {
        List<UserEntity> users = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet datatypeSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = datatypeSheet.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            UserEntity userEntity = new UserEntity();
            Row currentRow = iterator.next();
            Cell phoneCell = currentRow.getCell(0);
            if (phoneCell != null) {
                userEntity.setPhone(phoneCell.getStringCellValue());
                if (!userEntity.getPhone().startsWith("0")) {
                    throw new Exception("phone is valid");
                }
                userEntity = getInseeCode(currentRow.getCell(1), userEntity);
                userEntity = getName(currentRow.getCell(2), userEntity);
                userEntity = getCity(currentRow.getCell(4), userEntity);
                userEntity = getDistrict(currentRow.getCell(3), userEntity);
                userEntity = getCements(currentRow.getCell(5), userEntity);
                users.add(userEntity);
            }
        }
        workbook.close();
        return users;
    }


    private UserEntity getCity(Cell cell, UserEntity userEntity) throws Exception {
        String strCity = cell.getStringCellValue();
        strCity = strCity.replaceAll("Tỉnh", "");
        strCity = strCity.replaceAll("Thành phố", "");
        strCity = strCity.trim();
        int cityId = City.findByString(strCity);
        if (cityId == 0) {
            throw new Exception("city is 0");
        }
        userEntity.setCityId(cityId);
        return userEntity;
    }

    private UserEntity getDistrict(Cell cell, UserEntity userEntity) throws Exception {
        String strDistrict = cell.getStringCellValue();
        int district = City.detectDistrict(strDistrict);
        if (district == 0) {
            throw new Exception("district is 0");
        }
        userEntity.setDistrictId(district);
    return userEntity;
    }


    private UserEntity getName(Cell cell, UserEntity userEntity) {
        userEntity.setName(cell.getStringCellValue());
        return userEntity;
    }

    private UserEntity getInseeCode(Cell cell, UserEntity userEntity) {
        String code = cell.getStringCellValue();
        if (code.startsWith("VNAN")) {
            userEntity.setInseeId(code);
        }
        return userEntity;
    }

    private UserEntity getCements(Cell cell, UserEntity userEntity) {
        try {
            List<Integer> rs = new ArrayList<>();
            String str = cell.getStringCellValue();
            str = str.trim();
            if (str.contains("INSEE Power - S")) {
                rs.add(1);
            }
            if (str.contains("Wall Pro")) {
                rs.add(2);
            }

            if (str.contains("Lavilla")) {
                rs.add(3);
            }

            if (str.contains("Extra")) {
                rs.add(4);
            }
            userEntity.setProducts(rs);
        } catch (Exception e) {
        }
        return userEntity;
    }

}
