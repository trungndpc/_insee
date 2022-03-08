package vn.insee.admin.retailer.util;

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
import java.util.Iterator;

@Component
public class ReadFileExcelUtil {

    @Autowired
    UserRepository userRepository;


    public void readUser() {
        try {
           InputStream inputStream = City.class.getResourceAsStream("../../../../../data/CAMPAIGN_INSEE_ZALO_USER.xlsx");
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row firstRow = iterator.next();
            Cell firstCell = firstRow.getCell(0);
            System.out.println(firstCell.getStringCellValue());
            while (iterator.hasNext()) {
                try{
                    UserEntity userEntity = new UserEntity();
                    Row currentRow = iterator.next();
//                    userEntity.setId((int)Float.parseFloat(currentRow.getCell(0).toString()));
                    userEntity.setFollowerId(currentRow.getCell(1).getStringCellValue());
                    userEntity.setPhone(currentRow.getCell(2).getStringCellValue());
                    userEntity.setName(currentRow.getCell(3).getStringCellValue());
                    if (currentRow.getCell(5) != null) {
                        userEntity.setAvatar(currentRow.getCell(5).getStringCellValue());
                    }
//                    System.out.println(currentRow.getCell(6).getStringCellValue());
//                    int status = Integer.parseInt(currentRow.getCell(6).getStringCellValue());
//                    if (status != 1) {
//                        throw new Exception("status != 1");
//                    }
                    String strCity = currentRow.getCell(8).getStringCellValue();
                    strCity = strCity.replaceAll("Tỉnh", "");
                    strCity = strCity.replaceAll("Thành phố", "");
                    strCity = strCity.trim();
                    int cityId = City.findByString(strCity);
                    userEntity.setCityId(cityId);

                    String strDistrict = currentRow.getCell(9).getStringCellValue();
                    int district = City.detectDistrict(strDistrict);
                    userEntity.setDistrictId(district);
                    userEntity.setAddress(currentRow.getCell(10).getStringCellValue());
                    userEntity.setName(currentRow.getCell(11).getStringCellValue());
                    userEntity.setStatus(StatusUser.APPROVED);
//                    userRepository.saveAndFlush(userEntity);
                    System.out.println(userEntity.getId() + " | " + userEntity.getPhone()  + " | " + userEntity.getName() + " | " + userEntity.getAvatar());
                }catch (Exception e) {
//                    e.printStackTrace();
                }

            }

            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
