package vn.insee.admin.retailer.controller.importer;

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
    public static final CustomerExcelImporter INSTANCE = new CustomerExcelImporter();

    public List<UserEntity> read(InputStream inputStream) {
        try {
            List<UserEntity> users = new ArrayList<>();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                try {
                    UserEntity userEntity = new UserEntity();
                    Row currentRow = iterator.next();
                    userEntity.setPhone(currentRow.getCell(2).getStringCellValue());
                    userEntity = getCity(currentRow.getCell(8), userEntity);
                    userEntity = getDistrict(currentRow.getCell(9), userEntity);
                    userEntity = getAddress(currentRow.getCell(10), userEntity);
                    userEntity = getInseeCode(currentRow.getCell(13), userEntity);
                    userEntity = getName(currentRow.getCell(3), currentRow.getCell(11), userEntity);
                    userEntity = getCements(currentRow.getCell(12), userEntity);
                    users.add(userEntity);
                } catch (Exception e) {
                }
            }
            workbook.close();
            return users;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private UserEntity getCity(Cell cell, UserEntity userEntity) {
        try {
            String strCity = cell.getStringCellValue();
            strCity = strCity.replaceAll("Tỉnh", "");
            strCity = strCity.replaceAll("Thành phố", "");
            strCity = strCity.trim();
            int cityId = City.findByString(strCity);
            if (cityId == 0) {
                throw new Exception("city is 0");
            }
            userEntity.setCityId(cityId);
        } catch (Exception e) {
            System.out.println("phone: " + userEntity.getPhone() + " get City failed!: " + e.getMessage());
        }
        return userEntity;
    }

    private UserEntity getDistrict(Cell cell, UserEntity userEntity) {
        try {
            String strDistrict = cell.getStringCellValue();
            int district = City.detectDistrict(strDistrict);
            if (district == 0) {
                throw new Exception("city is 0");
            }
            userEntity.setDistrictId(district);
        } catch (Exception e) {
            System.out.println("phone: " + userEntity.getPhone() + " get District failed!: " + e.getMessage());
        }
        return userEntity;
    }

    private UserEntity getAddress(Cell cell, UserEntity userEntity) {
        try {
            userEntity.setAddress(cell.getStringCellValue());
        } catch (Exception e) {
            System.out.println("phone: " + userEntity.getPhone() + " get Address failed!: " + e.getMessage());
        }
        return userEntity;
    }

    private UserEntity getName(Cell cell, Cell cell2, UserEntity userEntity) {
        try {
            if (userEntity.getInseeId() != null) {
                userEntity.setName(cell2.getStringCellValue());
            } else {
                userEntity.setName(cell.getStringCellValue());
            }
        } catch (Exception e) {
            System.out.println("phone: " + userEntity.getPhone() + " get Name failed!: " + e.getMessage());
        }
        return userEntity;
    }

    private UserEntity getInseeCode(Cell cell, UserEntity userEntity) {
        try {
            String code = cell.getStringCellValue();
            if (code.startsWith("VNAN")) {
                userEntity.setInseeId(code);
            }
        } catch (Exception e) {
        }
        return userEntity;
    }

    private UserEntity getCements(Cell cell, UserEntity userEntity) {
        try {
            List<Integer> rs = new ArrayList<>();
            String str = cell.getStringCellValue();
            if (str.contains("Power-S")) {
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
