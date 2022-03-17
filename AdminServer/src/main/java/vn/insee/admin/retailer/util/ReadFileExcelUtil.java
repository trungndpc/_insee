package vn.insee.admin.retailer.util;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.message.User;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.repository.UserRepository;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ReadFileExcelUtil {

    @Autowired
    UserRepository userRepository;

    private List<String> readFile2List(InputStream inputStream) throws IOException {
        return IOUtils.readLines(inputStream, "UTF-8");
    }

    private UserEntity getCity(Cell cell, UserEntity userEntity, List<String> phones) {
        try{
            String strCity = cell.getStringCellValue();
            strCity = strCity.replaceAll("Tỉnh", "");
            strCity = strCity.replaceAll("Thành phố", "");
            strCity = strCity.trim();
            int cityId = City.findByString(strCity);
            if (cityId == 0) {
                throw new Exception("city is 0");
            }
            userEntity.setCityId(cityId);
        }catch (Exception e) {
            if (phones.contains(userEntity.getPhone())) {
                System.out.println("phone: " + userEntity.getPhone() + " get City failed!: "  + e.getMessage());
            }
        }
        return userEntity;
    }

    private UserEntity getDistrict(Cell cell, UserEntity userEntity, List<String> phones) {
        try{
            String strDistrict = cell.getStringCellValue();
            int district = City.detectDistrict(strDistrict);
            if (district == 0) {
                throw new Exception("city is 0");
            }
            userEntity.setDistrictId(district);
        }catch (Exception e) {
            if (phones.contains(userEntity.getPhone())) {
                System.out.println("phone: " + userEntity.getPhone() + " get District failed!: "  + e.getMessage());
            }
        }
        return userEntity;
    }

    private UserEntity getAddress(Cell cell, UserEntity userEntity, List<String> phones) {
        try{
            userEntity.setAddress(cell.getStringCellValue());
        }catch (Exception e) {
            if (phones.contains(userEntity.getPhone())) {
                System.out.println("phone: " + userEntity.getPhone() + " get Address failed!: "  + e.getMessage());
            }
        }
        return userEntity;
    }

    private UserEntity getAvatar(Cell cell, UserEntity userEntity, List<String> phones) {
        try {
            userEntity.setAvatar(cell.getStringCellValue());
        }catch (Exception e) {
            if (phones.contains(userEntity.getPhone())) {
                System.out.println("phone: " + userEntity.getPhone() + " get Avatar failed!: "  + e.getMessage());
            }
        }
        return userEntity;
    }

    private UserEntity getName(Cell cell, Cell cell2, UserEntity userEntity, List<String> phones) {
        try {
            if (userEntity.getInseeId() != null) {
                userEntity.setName(cell2.getStringCellValue());
            }else {
                userEntity.setName(cell.getStringCellValue());
            }
        }catch (Exception e) {
            if (phones.contains(userEntity.getPhone())) {
                System.out.println("phone: " + userEntity.getPhone() + " get Name failed!: "  + e.getMessage());
            }
        }
        return userEntity;
    }

    private UserEntity getInseeCode(Cell cell, UserEntity userEntity) {
        try {
            String code = cell.getStringCellValue();
            if (code.startsWith("VNAN")) {
                userEntity.setInseeId(code);
            }
        }catch (Exception e) {
//            System.out.printf("phone: " + userEntity.getPhone() + " get Insee code failed!: "  + e.getMessage());
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
        }catch (Exception e) {
//            System.out.println("phone: " + userEntity.getPhone() + " get Cements failed!: "  + e.getMessage());
        }
        return userEntity;
    }

    public void readUser() {
        try {
            List<String> phones = readFile2List(City.class.getResourceAsStream("../../../../../data/CUSTOMER.txt"));
            InputStream inputStream = City.class.getResourceAsStream("../../../../../data/CAMPAIGN_INSEE_ZALO_USER.xlsx");
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row firstRow = iterator.next();
            int count = 0;
            List<String> phone_2 = new ArrayList<>();
            while (iterator.hasNext()) {
                try{
                    UserEntity userEntity = new UserEntity();
                    Row currentRow = iterator.next();
                    userEntity.setFollowerId(currentRow.getCell(1).getStringCellValue());
                    userEntity.setPhone(currentRow.getCell(2).getStringCellValue());
                    userEntity = getAvatar(currentRow.getCell(5), userEntity, phones);
                    userEntity = getCity(currentRow.getCell(8), userEntity, phones);
                    userEntity = getDistrict(currentRow.getCell(9), userEntity, phones);
                    userEntity = getAddress(currentRow.getCell(10), userEntity, phones);
                    userEntity = getInseeCode(currentRow.getCell(13),userEntity);
                    userEntity = getName(currentRow.getCell(3), currentRow.getCell(11), userEntity, phones);
                    userEntity = getCements(currentRow.getCell(12), userEntity);
                    userEntity.setStatus(StatusUser.APPROVED);
                    if (phones.contains(userEntity.getPhone())) {
                        phone_2.add(userEntity.getPhone());
                        userRepository.saveAndFlush(userEntity);
                        count++;
                    }
                }catch (Exception e) {
                }
            }
            System.out.println("COUNT: " + count);
            for (String phone: phones) {
                if (!phone_2.contains(phone)) {
                    System.out.println("PHONE: " + phone);
                }
            }
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readUserV2() {
        try {
            List<String> phones = readFile2List(City.class.getResourceAsStream("../../../../../data/CUSTOMER.txt"));
            InputStream inputStream = City.class.getResourceAsStream("../../../../../data/CAMPAIGN_INSEE_ZALO_USER.xlsx");
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row firstRow = iterator.next();
            int count = 0;
            List<String> phone_2 = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            while (iterator.hasNext()) {
                try{
                    UserEntity userEntity = new UserEntity();
                    Row currentRow = iterator.next();
                    userEntity.setFollowerId(currentRow.getCell(1).getStringCellValue());
                    userEntity.setPhone(currentRow.getCell(2).getStringCellValue());
                    userEntity.setZaloId(currentRow.getCell(7).getStringCellValue());
                    if (phones.contains(userEntity.getPhone())) {
                        phone_2.add(userEntity.getPhone());
                        builder.append(userEntity.getPhone() + ":" + userEntity.getZaloId());
                        builder.append(",");
                        count++;
                    }
                }catch (Exception e) {
                }
            }
            System.out.println("COUNT: " + count);
            System.out.println(builder.toString());
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
